package biblioteca.repositorio;

import biblioteca.modelo.Livro;
import simplodb.ArquivoMotor;
import simplodb.Repositorio;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LivroRepositorio extends Repositorio<Livro> {

    public LivroRepositorio(ArquivoMotor motor) {
        super(motor, "livros");
    }

    /**
     * Busca um livro pelo ISBN (fornecido — exemplo de buscarComFiltro com lambda).
     */
    public Optional<Livro> buscarPorIsbn(String isbn) {
        return buscarComFiltro(l -> l.getIsbn().equals(isbn))
                .stream()
                .findFirst();
    }

    /**
     * Retorna todos os livros de um gênero, ordenados do mais recente para o mais antigo.
     *
     * @param genero gênero a filtrar (case-insensitive)
     * @return lista ordenada por anoPublicacao descendente
     */
    public List<Livro> buscarPorGeneroCoordenado(String genero) {
        return buscarTodos().stream()
                .filter(l -> l.getGenero().equalsIgnoreCase(genero))
                .sorted(Comparator.comparingInt(Livro::getAnoPublicacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Agrupa todos os livros por gênero.
     *
     * @return Map onde a chave é o gênero e o valor é a lista de livros daquele gênero
     */
    public Map<String, List<Livro>> agruparPorGenero() {
        return buscarTodos().stream()
                .collect(Collectors.groupingBy(Livro::getGenero));
    }
}
