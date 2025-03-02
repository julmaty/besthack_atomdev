package com.example.besthack_atomdev.service;

import com.example.besthack_atomdev.common.OilBase;
import com.example.besthack_atomdev.dto.LotListRequest;
import com.example.besthack_atomdev.model.Lot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.example.besthack_atomdev.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.besthack_atomdev.common.FuelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;

    public Page<Lot> getAllLots(LotListRequest request) {
        // Инициализация фильтров
        List<Integer> oilBaseCodes = request.getOilBaseCodes();
        List<Integer> fuelTypeCodes = request.getFuelTypeCodes();

        // Поиск по строке (если передана)
        List<Integer> matchingOilBaseCodes = null;
        List<Integer> matchingFuelTypeCodes = null;
        if (request.getSearchString() != null && !request.getSearchString().isEmpty()) {
            String searchString = request.getSearchString().toLowerCase();

            // Поиск совпадений в OilBase
            matchingOilBaseCodes = Arrays.stream(OilBase.values())
                    .filter(oilBase -> oilBase.getName().toLowerCase().contains(searchString) ||
                            oilBase.getRegion().toLowerCase().contains(searchString))
                    .map(OilBase::getCode)
                    .collect(Collectors.toList());

            // Поиск совпадений в FuelType
            matchingFuelTypeCodes = Arrays.stream(FuelType.values())
                    .filter(fuelType -> fuelType.getDescription().toLowerCase().contains(searchString))
                    .map(FuelType::getCode)
                    .collect(Collectors.toList());
        }

        // Логика объединения фильтров для OilBase
        List<Integer> finalOilBaseCodes = mergeFilters(oilBaseCodes, matchingOilBaseCodes);

        // Логика объединения фильтров для FuelType
        List<Integer> finalFuelTypeCodes = mergeFilters(fuelTypeCodes, matchingFuelTypeCodes);

        // Преобразование кодов в объекты OilBase и FuelType
        List<OilBase> finalOilBases = convertToOilBases(finalOilBaseCodes);
        List<FuelType> finalFuelTypes = convertToFuelTypes(finalFuelTypeCodes);

        // Пагинация
        int page = request.getPage();
        int size = 10; // Количество элементов на странице
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        // Вызов метода репозитория
        return lotRepository.findFiltered(
                finalOilBases,
                finalFuelTypes,
                pageRequest
        );
    }

    /**
     * Метод для объединения двух списков фильтров:
     * - Если оба списка не пустые, берется их пересечение.
     * - Если один из списков пустой, используется оставшийся.
     * - Если оба пустые, возвращается null.
     */
    private List<Integer> mergeFilters(List<Integer> filter1, List<Integer> filter2) {
        if (filter1 == null || filter1.isEmpty()) {
            return filter2;
        }
        if (filter2 == null || filter2.isEmpty()) {
            return filter1;
        }

        // Пересечение двух списков
        Set<Integer> intersection = new HashSet<>(filter1);
        intersection.retainAll(filter2);
        return intersection.isEmpty() ? null : new ArrayList<>(intersection);
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
                    OilBase oilBase = OilBase.fromCode(kscssNbCode); // Преобразуем код в enum
                    int kscssFuelCode = Integer.parseInt(record.get(2)); // Код КССС Топлива
                    FuelType fuelType = FuelType.fromCode(kscssFuelCode); // Проверяем, что код топлива существует
                    double startWeight = Double.parseDouble(record.get(3)); // Стартовой вес
                    double availableBalance = Double.parseDouble(record.get(4)); // Доступный остаток
                    double pricePerTon = Double.parseDouble(record.get(5)); // Цена за 1 тонну

                    // Создание объекта Lot
                    Lot lot = new Lot();
                    lot.setLotDate(lotDate);
                    lot.setOilBase(oilBase);
                    lot.setFuelType(fuelType);
                    lot.setStartWeight(startWeight);
                    lot.setAvailableBalance(availableBalance);
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

    private List<OilBase> convertToOilBases(List<Integer> codes) {
        if (codes == null || codes.isEmpty()) {
            return null;
        }
        return codes.stream()
                .map(code -> OilBase.fromCode(code)) // Преобразуем код в объект OilBase
                .collect(Collectors.toList());
    }

    private List<FuelType> convertToFuelTypes(List<Integer> codes) {
        if (codes == null || codes.isEmpty()) {
            return null;
        }
        return codes.stream()
                .map(code -> FuelType.fromCode(code)) // Преобразуем код в объект FuelType
                .collect(Collectors.toList());
    }
}
