# Exercícios — Trabalhando com Datas em Java

---

### Exercício 1 — Calculadora de Idade

Implemente o método `calcularIdade()` que recebe uma data de nascimento e exibe:
- A idade em anos completos e meses restantes
- Se a pessoa é maior de idade
- Quantos dias faltam para o próximo aniversário

```java
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class CalculadoraIdade {

    public static void main(String[] args) {
        calcularIdade(LocalDate.of(1995, 6, 20));
        calcularIdade(LocalDate.of(2010, 12, 1));
    }

    public static void calcularIdade(LocalDate nascimento) {
        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(nascimento, hoje);

        System.out.printf("Idade: %d anos e %d meses%n",
            periodo.getYears(), periodo.getMonths());
        System.out.println("Maior de idade: " + (periodo.getYears() >= 18));

        // TODO: calcular quantos dias faltam para o próximo aniversário
        // Dica: use withYear() para ajustar o ano e ChronoUnit.DAYS.between()
        LocalDate proximoAniversario = nascimento.withYear(hoje.getYear());
        if (!proximoAniversario.isAfter(hoje)) {
            proximoAniversario = proximoAniversario.plusYears(1);
        }
        long diasParaAniversario = 0; // TODO: substituir pelo cálculo correto
        System.out.println("Dias para o próximo aniversário: " + diasParaAniversario);
        System.out.println();
    }
}
```

**Saída esperada (considerando hoje = 2026-04-26):**
```
Idade: 30 anos e 10 meses
Maior de idade: true
Dias para o próximo aniversário: 55

Idade: 15 anos e 4 meses
Maior de idade: false
Dias para o próximo aniversário: 219
```

---

### Exercício 2 — Formatador de Datas

Implemente o método `parsearData()` que receba uma `String` em qualquer dos formatos abaixo e retorne um `LocalDate`. O método deve tentar cada formato e lançar `IllegalArgumentException` caso nenhum funcione.

- `"dd/MM/yyyy"` → ex: `"24/04/2024"`
- `"yyyy-MM-dd"` → ex: `"2024-04-24"`
- `"dd-MM-yyyy"` → ex: `"24-04-2024"`

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FormatadorDatas {

    public static void main(String[] args) {
        System.out.println(parsearData("24/04/2024"));
        System.out.println(parsearData("2024-04-24"));
        System.out.println(parsearData("24-04-2024"));
    }

    public static LocalDate parsearData(String texto) {
        // TODO: tentar parsear com cada um dos três formatos
        // Dica: capture DateTimeParseException e tente o próximo formato
        return null;
    }
}
```

**Saída esperada:**
```
2024-04-24
2024-04-24
2024-04-24
```

---

### Exercício 3 — Sistema de Reservas

Implemente a classe `Reserva` com os atributos e métodos abaixo.

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private LocalDate dataEntrada;
    private LocalDate dataSaida;

    public Reserva(LocalDate dataEntrada, LocalDate dataSaida) {
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    // TODO: retornar o número de noites entre entrada e saída
    public long quantidadeDeNoites() {
        return 0;
    }

    // TODO: retornar true se hoje está dentro do período [dataEntrada, dataSaida)
    public boolean estaAtiva() {
        return false;
    }

    // TODO: formato "Reserva de 24/04/2026 a 30/04/2026 (6 noites)"
    @Override
    public String toString() {
        return "";
    }

    public static void main(String[] args) {
        Reserva r1 = new Reserva(LocalDate.of(2026, 4, 24), LocalDate.of(2026, 4, 30));
        Reserva r2 = new Reserva(LocalDate.of(2026, 5, 10), LocalDate.of(2026, 5, 15));

        System.out.println(r1);
        System.out.println("Ativa: " + r1.estaAtiva());
        System.out.println();
        System.out.println(r2);
        System.out.println("Ativa: " + r2.estaAtiva());
    }
}
```

**Saída esperada (considerando hoje = 2026-04-26):**
```
Reserva de 24/04/2026 a 30/04/2026 (6 noites)
Ativa: true

Reserva de 10/05/2026 a 15/05/2026 (5 noites)
Ativa: false
```

---

### Exercício 4 — Conversor de Fuso Horário

Implemente o método `converterFuso()` que receba um `LocalDateTime` no horário de São Paulo e uma `String` com o fuso de destino, e retorne o horário equivalente formatado como `"dd/MM/yyyy HH:mm z"`.

```java
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConversorFuso {

    public static void main(String[] args) {
        LocalDateTime reuniaoSP = LocalDateTime.of(2024, 6, 15, 10, 0);

        System.out.println(converterFuso(reuniaoSP, "America/New_York"));
        System.out.println(converterFuso(reuniaoSP, "Europe/London"));
        System.out.println(converterFuso(reuniaoSP, "Asia/Tokyo"));
    }

    public static String converterFuso(LocalDateTime dataHoraSP, String fusoDestino) {
        // TODO: converter o LocalDateTime de São Paulo para o fuso de destino
        // Dica: use atZone() para associar o fuso de origem e withZoneSameInstant() para converter
        return "";
    }
}
```

**Saída esperada:**
```
15/06/2024 09:00 EDT
15/06/2024 15:00 BST
16/06/2024 00:00 JST
```
