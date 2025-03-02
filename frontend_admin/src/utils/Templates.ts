

//справочник соответствия названия вида топлива и его кода
import {FuelCodesAndNamesMappingType, OilDepotsCodesAndNamesMappingType} from "@/utils/Types";

export const FuelCodesAndNamesMapping:FuelCodesAndNamesMappingType= [
    {
        code: 2001,
        name: 'АИ-92'
    },
    {
        code: 2002,
        name: 'АИ-95'
    },
    {
        code: 2003,
        name: 'АИ-92 Экто'
    },
    {
        code: 2004,
        name: 'АИ-95 Экто'
    },
    {
        code: 2005,
        name: 'ДТ'
    }
];

export const OilDepotsCodesAndNamesMapping:OilDepotsCodesAndNamesMappingType= [
        {
            code: 1001,
            name: 'Нефтебаза 1',
            region:'Московская область',
        },
        {
            code: 1002,
            name: 'Нефтебаза 2',
            region:'Ленинградская область',
        },
        {
            code: 1003,
            name: 'Нефтебаза 3',
            region:'Ростовская область',
        },
        {
            code: 1004,
            name: 'Нефтебаза 4',
            region:'Краснодарский край',
        },
        {
            code: 1005,
            name: 'Нефтебаза 5',
            region:'Свердловская область',
        },
        {
            code: 1006,
            name: 'Нефтебаза 6',
            region:'Тюменская область',
        },
        {
            code: 1007,
            name: 'Нефтебаза 7',
            region:'Нижегородская область',
        },
        {
            code: 1008,
            name: 'Нефтебаза 8',
            region:'Самарская область',
        },
        {
            code: 1009,
            name: 'Нефтебаза 9',
            region:'Волгоградская область',
        },
        {
            code: 1010,
            name: 'Нефтебаза 10',
            region:'Челябинская область',
        }
    ];

export const statusMappingToText={
    SOLD:'Продано',
    INACTIVE:'Неактивен',
    CONFIRMED:'Подтвержден',
}

export const  deliveryMappingToText={
    SELF_PICKUP:'Самовывоз',
    DELIVERY:'Доставка'
}