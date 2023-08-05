package com.paulo.budgeting.exporters;

import com.paulo.budgeting.domain.Budget;
import com.paulo.budgeting.dto.BudgetDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor
public class BudgetToCsvExporter implements Exporter<String> {

    @Override
    public String export(BudgetDto budgetDto) throws IOException {
        StringWriter sw = new StringWriter();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
//                .setHeader(new String[]{"Title", "Cost"})//Dont set a header
                .build();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            printer.printRecord(budgetDto.getTitle());
            printer.printRecord("");
            printer.printRecord("Income");
            printer.printRecord("");

            budgetDto.getIncomes()
                    .forEach(income -> {
                        try {
                            printer.printRecord(income.getTitle(), income.getValue().toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

            printer.printRecord("");
            printer.printRecord("Expense");
            printer.printRecord("");

            budgetDto.getExpenses()
                    .forEach((expense1) -> {
                        try {
                            printer.printRecord(expense1.getTitle(), expense1.getValue().toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }


        return sw.toString();
    }
}
