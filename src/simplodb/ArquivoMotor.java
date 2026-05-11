package simplodb;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Motor de persistência do SimploDB.
 *
 * Armazena cada objeto em um arquivo separado usando serialização Java:
 *   db/<entidade>/<id>.dat
 *
 * Exemplo:
 *   db/livros/1.dat
 *   db/livros/2.dat
 *   db/usuarios/1.dat
 */
public class ArquivoMotor {

    private final Path diretorioBase;

    public ArquivoMotor(Path diretorioBase) {
        this.diretorioBase = diretorioBase;
    }

    // -------------------------------------------------------------------------
    // Método auxiliar — fornecido como exemplo de uso da API NIO.2
    // -------------------------------------------------------------------------

    private Path resolverCaminho(String entidade, Long id) {
        return diretorioBase.resolve(entidade).resolve(id + ".dat");
    }

    // -------------------------------------------------------------------------
    // TODO Exercício 5a — IO Streams (Módulo 5)
    // -------------------------------------------------------------------------

    /**
     * Serializa o objeto e grava no arquivo correspondente.
     * Cria o diretório da entidade se ainda não existir.
     *
     * Arquivo de destino: db/<entidade>/<id>.dat
     *
     * Passos:
     *   1. Resolva o diretório da entidade: diretorioBase.resolve(entidade)
     *   2. Use Files.createDirectories(dir) para garantir que o diretório existe
     *   3. Resolva o caminho do arquivo: resolverCaminho(entidade, id)
     *   4. Abra um OutputStream com Files.newOutputStream(caminho)
     *   5. Envolva em ObjectOutputStream e chame writeObject(obj)
     *   Dica: use try-with-resources para fechar os streams automaticamente
     */
    public void salvar(String entidade, Long id, Object obj) throws IOException {
        // TODO Exercício 5a
        // 1. Resolva o diretório da entidade
        Path dir = diretorioBase.resolve(entidade);

        // 2. Garante que o diretório existe
        Files.createDirectories(dir);

        // 3. Resolva o caminho do arquivo (ex: db/livros/1.dat)
        Path caminho = resolverCaminho(entidade, id);

        // 4 e 5. Abre o stream e serializa o objeto
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(caminho))) {
            oos.writeObject(obj);
        }
    }

    // -------------------------------------------------------------------------
    // TODO Exercício 5b — IO Streams (Módulo 5)
    // -------------------------------------------------------------------------

    /**
     * Desserializa e retorna o objeto do arquivo correspondente.
     * Retorna Optional.empty() se o arquivo não existir.
     *
     * Passos:
     *   1. Resolva o caminho do arquivo com resolverCaminho(entidade, id)
     *   2. Se Files.notExists(caminho), retorne Optional.empty()
     *   3. Abra um InputStream com Files.newInputStream(caminho)
     *   4. Envolva em ObjectInputStream e chame readObject()
     *   5. Faça o cast para T e retorne Optional.of(resultado)
     *   Dica: use try-with-resources para fechar os streams automaticamente
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> carregar(String entidade, Long id) throws IOException, ClassNotFoundException {
        // TODO Exercício 5b
        // 1. Resolva o caminho do arquivo
        Path caminho = resolverCaminho(entidade, id);

        // 2. Se o arquivo não existir, retorna um opcional vazio
        if (Files.notExists(caminho)) {
            return Optional.empty();
        }

        // 3, 4 e 5. Abre o stream, lê o objeto e faz o cast para o tipo T
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(caminho))) {
            T resultado = (T) ois.readObject();
            return Optional.of(resultado);
        }
    }

    // -------------------------------------------------------------------------
    // TODO Exercício 4 — NIO.2 (Módulo 4)
    // -------------------------------------------------------------------------

    /**
     * Carrega todos os objetos de uma entidade lendo o diretório de arquivos .dat.
     * Retorna lista vazia se o diretório não existir.
     *
     * Passos:
     *   1. Resolva o diretório: diretorioBase.resolve(entidade)
     *   2. Se Files.notExists(dir), retorne List.of()
     *   3. Use Files.list(dir) para obter um Stream<Path> dos arquivos
     *      Dica: Files.list() precisa ser fechado — use try-with-resources
     *   4. Para cada Path, extraia o ID do nome do arquivo (sem extensão):
     *        Long id = Long.parseLong(path.getFileName().toString().replace(".dat", ""))
     *   5. Chame carregar(entidade, id) para cada arquivo
     *   6. Filtre os presentes (Optional::isPresent) e extraia o valor (.map(Optional::get))
     *   7. Colete em uma List<T> com .collect(Collectors.toList())
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> carregarTodos(String entidade) throws IOException, ClassNotFoundException {

        // 1. Resolve o diretório da entidade
        Path dir = diretorioBase.resolve(entidade);

        // 2. Se não existir, retorna lista vazia
        if (Files.notExists(dir)) {
            return List.of();
        }

        // 3. Files.list precisa de try-with-resources
        try (Stream<Path> arquivos = Files.list(dir)) {

            return arquivos

                    // Filtra apenas arquivos .dat
                    .filter(path -> path.toString().endsWith(".dat"))

                    // 4. Extrai o ID do nome do arquivo
                    .map(path -> {
                        String nomeArquivo = path.getFileName().toString();
                        Long id = Long.parseLong(nomeArquivo.replace(".dat", ""));
                        return id;
                    })

                    // 5. Chama carregar(entidade, id)
                    .map(id -> {
                        try {
                            return (Optional<T>) carregar(entidade, id);
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })

                    // 6. Filtra Optional presentes
                    .filter(Optional::isPresent)

                    // Extrai o objeto do Optional
                    .map(Optional::get)

                    // 7. Coleta em lista
                    .collect(Collectors.toList());
        }
    }

    // -------------------------------------------------------------------------
    // Fornecido — deletar arquivo
    // -------------------------------------------------------------------------

    public boolean deletar(String entidade, Long id) throws IOException {
        Path caminho = resolverCaminho(entidade, id);
        return Files.deleteIfExists(caminho);
    }
}