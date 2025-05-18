package com.example.scanner;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

@Service
public class S3FileService {
    private final S3AsyncClient s3Client;

    public S3FileService(S3AsyncClient s3Client) {
        this.s3Client = s3Client;
    }

    public Mono<ByteBuffer> readFile(String bucket, String key) {
        GetObjectRequest request = GetObjectRequest.builder().bucket(bucket).key(key).build();
        CompletableFuture<ByteBuffer> future = new CompletableFuture<>();
        s3Client.getObject(request, new AsyncResponseTransformer<>() {
            private ByteBuffer data = ByteBuffer.allocate(0);

            @Override
            public CompletableFuture<ByteBuffer> prepare() {
                return future;
            }

            @Override
            public void onResponse(Object response) {}

            @Override
            public void onStream(SdkPublisher<ByteBuffer> publisher) {
                publisher.subscribe(byteBuffer -> {
                    ByteBuffer newData = ByteBuffer.allocate(data.remaining() + byteBuffer.remaining());
                    newData.put(data).put(byteBuffer).flip();
                    data = newData;
                }, future::completeExceptionally, () -> future.complete(data));
            }

            @Override
            public void exceptionOccurred(Throwable error) {
                future.completeExceptionally(error);
            }
        });
        return Mono.fromFuture(future);
    }
}
