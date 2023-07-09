package com.paulo.budgeting;

import com.paulo.budgeting.domain.MoneyItem;
import com.paulo.budgeting.domain.enums.MoneyItemType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BudgetZoneApplicationTests {

	@Test
	void contextLoads() throws IOException {
		FileWriter out = new FileWriter("book_new.csv");

		List<MoneyItem> expenseList = new ArrayList<>();

		expenseList.add(MoneyItem.builder().type(MoneyItemType.EXPENSE).value(BigDecimal.ONE).title("Harriet").build());
		expenseList.add(MoneyItem.builder().type(MoneyItemType.EXPENSE).value(BigDecimal.TEN).title("Namdy").build());

		StringWriter sw = new StringWriter();

		CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
				.setHeader(new String[]{"Title", "Cost"})
				.build();
//		csvFormat.print(out);

		try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
			expenseList.forEach((expense1) -> {
				try {
//					csvFormat.print(out);

					printer.printRecord(expense1.getTitle(), expense1.getValue().toString());

				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

//		return sw.toString();
//		System.out.println(sw.toString());
//		out.write(sw.toString());
//		out.flush();
//		assertEquals(EXPECTED_FILESTREAM, sw.toString().trim());
	}

	@Test
	void contextLoads1() throws IOException {
//		FileWriter out = new FileWriter("book_new.csv");

		List<MoneyItem> expenseList = new ArrayList<>();

		expenseList.add(MoneyItem.builder().type(MoneyItemType.EXPENSE).value((BigDecimal.ONE)).title("Harriet").build());
		expenseList.add(MoneyItem.builder().type(MoneyItemType.EXPENSE).value(BigDecimal.TEN).title("Namdy").build());

		StringWriter sw = new StringWriter();

		CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
				.setHeader(new String[]{"Title", "Cost"})
				.build();
//		csvFormat.print(out);

		try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
			expenseList.forEach((expense1) -> {
				try {
//					csvFormat.print(out);

					printer.printRecord(expense1.getTitle(), expense1.getValue().toString());

				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

//		return sw.toString();
		System.out.println(sw);
//		out.write(sw.toString());
//		out.flush();
//		assertEquals(EXPECTED_FILESTREAM, sw.toString().trim());
	}

}
