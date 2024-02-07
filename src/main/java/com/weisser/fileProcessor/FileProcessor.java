package com.weisser.fileProcessor;

import java.util.concurrent.*;

public class FileProcessor {
    private static final int N_THREADS = 4;
    private ExecutorService executor;
    private LinkedBlockingQueue<Runnable> queue;

    public FileProcessor() {
        executor = Executors.newFixedThreadPool(N_THREADS);
        queue = new LinkedBlockingQueue<>();
    }

    public void processFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            try {
                String fileContent = readFromFile(fileName);
                queue.put(() -> processJSON(fileContent)); // добавляем задачу в очередь
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Запускаем обработку файлов в пуле потоков
        while (!queue.isEmpty()) {
            try {
                executor.execute(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Ждем завершения всех задач в пуле потоков
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String readFromFile(String fileName) {
        // Чтение файла и возврат его содержимого в виде строки
        return null;
    }

    private void processJSON(String fileContent) {
        // Модификация JSON и сохранение результата
    }

    public static void main(String[] args) {
        FileProcessor processor = new FileProcessor();
        processor.processFiles(new String[]{"file1.json", "file2.json", /* другие файлы */});
    }
}
