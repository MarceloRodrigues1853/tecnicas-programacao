package com.paginacao;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class Main {

    static void main() {

        LocalDate data    = LocalDate.now();
        LocalTime horario = LocalTime.now();

        LocalDateTime dataHora = LocalDateTime.of(data, horario);
        System.out.println("of(data, horario): " + dataHora);

        dataHora = LocalDateTime.now();
        System.out.println("now():             " + dataHora);

        LocalDate now = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate dataParse = LocalDate.parse("1987-10-19");

        System.out.println(dataParse.getDayOfWeek());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.of("pt", "BR"));

        String dataFormatada = formatter.format(now);
        System.out.println(dataFormatada);

        //System.out.println(formatter.parse(dataFormatada));

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZonedDateTime first = zonedDateTime.with(TemporalAdjusters.firstDayOfMonth());

        System.out.println(first);


    }

}
