export interface LotCardResponse {
    availableBalance: 0
    fuelType: number;
    id: 9
    lotDate: string;
    lotPrice: 0
    oilBase: number;
    pricePerTon: 900
    region: string;
    startWeight: 3000
    status: string;
}

export interface LotCardType {
    lotNumber:number;
    fuelCode:number;
    fuelName: string;
    OilDepotsName: string;
    OilDepotsRegion: string;
    fuelPriceByOneTon: number;
    fuelQuantity: number;
}