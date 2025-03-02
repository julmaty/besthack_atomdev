import {Dayjs} from "dayjs";

export interface LotFullData {

    status: string;
    lotNumber: number;
    OilDepotsCode: number;
    fuelCode: number;
    createdDate: Dayjs;
    startingWeight: number;
    fuelPriceByOneTon: number;
    fuelQuantity: number;
}