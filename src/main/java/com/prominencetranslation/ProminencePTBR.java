package com.prominencetranslation;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class ProminencePTBR implements ModInitializer {

    public static final String MOD_ID = "prominence_ptbr";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Arquivo de flag: se existir, a tradução já foi instalada
    private static final String FLAG_FILE = "prominence_ptbr_installed.txt";

    @Override
    public void onInitialize() {
        LOGGER.info("[Prominence PT-BR] Iniciando verificação de tradução...");

        Path gameDir = FabricLoader.getInstance().getGameDir();
        Path flagFile = gameDir.resolve(FLAG_FILE);

        if (Files.exists(flagFile)) {
            LOGGER.info("[Prominence PT-BR] Tradução já instalada anteriormente. Pulando.");
            return;
        }

        try {
            instalarTraducao(gameDir);
            // Cria o arquivo de flag para não reinstalar nas próximas sessões
            Files.writeString(flagFile,
                "Tradução PT-BR instalada em: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n" +
                "Para reinstalar, delete este arquivo.\n"
            );
            LOGGER.info("[Prominence PT-BR] Tradução instalada com sucesso!");
        } catch (Exception e) {
            LOGGER.error("[Prominence PT-BR] Erro ao instalar tradução: " + e.getMessage(), e);
        }
    }

    private void instalarTraducao(Path gameDir) throws Exception {
        Path destino = gameDir.resolve("config/ftbquests/quests");
        Path backup = gameDir.resolve("config/ftbquests/quests_backup_" +
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        // Faz backup se a pasta de destino já existir
        if (Files.exists(destino)) {
            LOGGER.info("[Prominence PT-BR] Fazendo backup em: " + backup.getFileName());
            copiarDiretorio(destino, backup);
            LOGGER.info("[Prominence PT-BR] Backup concluído.");
        }

        // Copia os arquivos de tradução do mod para o destino
        // Os arquivos ficam em resources/quests/ dentro do .jar
        URI jarUri = ProminencePTBR.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        Path jarPath = Paths.get(jarUri);

        try (FileSystem jarFs = FileSystems.newFileSystem(jarPath, (ClassLoader) null)) {
            Path questsNoJar = jarFs.getPath("/quests");

            if (!Files.exists(questsNoJar)) {
                throw new FileNotFoundException("Pasta /quests não encontrada dentro do .jar do mod!");
            }

            // Apaga o destino antigo e copia o novo
            if (Files.exists(destino)) {
                deletarDiretorio(destino);
            }
            Files.createDirectories(destino);

            copiarDiretorioJar(questsNoJar, destino);
        }

        LOGGER.info("[Prominence PT-BR] Arquivos de quest copiados para: " + destino);
        LOGGER.info("[Prominence PT-BR] Total de capítulos: " + contarArquivos(destino, "chapters"));
    }

    private void copiarDiretorioJar(Path origem, Path destino) throws IOException {
        Files.walkFileTree(origem, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path rel = origem.relativize(dir);
                Path alvo = destino.resolve(rel.toString());
                Files.createDirectories(alvo);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path rel = origem.relativize(file);
                Path alvo = destino.resolve(rel.toString());
                Files.copy(file, alvo, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void copiarDiretorio(Path origem, Path destino) throws IOException {
        Files.walkFileTree(origem, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path alvo = destino.resolve(origem.relativize(dir));
                Files.createDirectories(alvo);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path alvo = destino.resolve(origem.relativize(file));
                Files.copy(file, alvo, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void deletarDiretorio(Path dir) throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path d, IOException exc) throws IOException {
                Files.delete(d);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private long contarArquivos(Path dir, String subpasta) {
        try (Stream<Path> stream = Files.list(dir.resolve(subpasta))) {
            return stream.count();
        } catch (IOException e) {
            return -1;
        }
    }
}
