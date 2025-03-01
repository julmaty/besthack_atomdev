package com.example.besthack_atomdev.service;

import com.example.besthack_atomdev.model.Lot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.example.besthack_atomdev.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;

    // Получить все лоты
    public List<Lot> getAllLots() {
        return lotRepository.findAll();
    }

    // Получить лот по ID
    public Optional<Lot> getLotById(long id) {
        return lotRepository.findById(id);
    }

    // Создать новый лот
    public Lot createLot(Lot lot) {
        return lotRepository.save(lot);
    }

    // Обновить лот
    public Lot updateLot(long id, Lot updatedLot) {
        Optional<Lot> existingLot = lotRepository.findById(id);
        if (existingLot.isPresent()) {
            updatedLot.setId(id); // Сохраняем ID
            return lotRepository.save(updatedLot);
        }
        return null; // Лот не найден
    }

    // Удалить лот
    public boolean deleteLot(long id) {
        Optional<Lot> lot = lotRepository.findById(id);
        if (lot.isPresent()) {
            lotRepository.deleteById(id);
            return true;
        }
        return false; // Лот не найден
    }

    // Метод загрузки CSV-файла
    public List<String> uploadLotsFromCsv(InputStream inputStream) {
        List<String> errors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withDelimiter(',') // Указываем разделитель
                     .withQuote('"') // Указываем символ кавычек
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            for (CSVRecord record : csvParser) {
                try {
                    // Парсинг полей из строки CSV
                    LocalDate lotDate = LocalDate.parse(record.get(0)); // Дата лота
                    int kscssNbCode = Integer.parseInt(record.get(1)); // Код КССС НБ
                    int kscssFuelCode = Integer.parseInt(record.get(2)); // Код КССС Топлива
                    double startWeight = Double.parseDouble(record.get(3)); // Стартовой вес
                    double availableBalance = Double.parseDouble(record.get(4)); // Доступный остаток
                    String status = record.get(5); // Статус
                    double pricePerTon = Double.parseDouble(record.get(6)); // Цена за 1 тонну

                    // Создание объекта Lot
                    Lot lot = new Lot();
                    lot.setLotDate(lotDate);
                    lot.setKscssNbCode(kscssNbCode);
                    lot.setKscssFuelCode(kscssFuelCode);
                    lot.setStartWeight(startWeight);
                    lot.setAvailableBalance(availableBalance);
                    lot.setStatus(status);
                    lot.setPricePerTon(pricePerTon);

                    // Сохранение лота в базу данных
                    lotRepository.save(lot);
                } catch (Exception e) {
                    // Если возникла ошибка, записываем сообщение об ошибке
                    errors.add("Ошибка в строке: " + record.toString() + ". Причина: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            errors.add("Ошибка при чтении CSV-файла: " + e.getMessage());
        }

        return errors; // Возвращаем список ошибок
    }
}
