'use client';
import './lots-list.css';
import Page from '@/components/Page/Page';
import TextField from "@/components/Universal/Form/TextField/TextField";
import Text from "@/components/Universal/Text/Text";
import {useCallback, useEffect, useMemo, useState} from "react";
import {FuelCodesAndNamesMapping, OilDepotsCodesAndNamesMapping} from "@/utils/Templates";
import {Checkbox, Flex, Form, List } from 'antd';
import { Card } from "antd";
import { Button } from "antd";
import { useRouter } from "next/navigation";
import Spacer from "@/components/Universal/Spacer/Spacer";
import useAPI from "@/utils/useAPI/useAPI";
import {isNil} from "lodash-es";

export default function LotsList() {

    const router = useRouter();

    const goToLotPage=useCallback((lotNumber:number)=>{
        router.push(`/lot-page?lotNumber=${lotNumber}`);
    },[router]);

    const [currentPage, setCurrentPage]=useState<number>(1);

    const onChangePagination=useCallback((current:number)=>{
        console.log('onChangePagination current', current);
        setCurrentPage(current);
    },[setCurrentPage]);

    const [form] = Form.useForm();

    const {checkedFuelList, checkedOilDepotsList, search} = Form.useWatch(({checkedFuelList, checkedOilDepotsList, search})=>({checkedFuelList, checkedOilDepotsList, search}), form)?? {
        checkedFuelList:[], checkedOilDepotsList:[], search:''
    };

    const [{data}] = useAPI<any>({
        requestMethod:'post',
        APIController: 'api',
        APIMethod: 'lots',
        requestBody:{
            searchString: search,
            oilBaseCodes: checkedOilDepotsList,
            fuelTypeCodes: checkedFuelList,
            page: currentPage-1,
        }
    });

    const [lotsList, setLotsList]=useState([]);

    const [totalElement, setTotalElement]=useState(0);

    useEffect(()=>{

        console.log('data', data);

        if(!isNil(data)){
            setTotalElement(data.totalElements);
            setLotsList(data.content.map(({
                availableBalance,
                fuelTypeCode,
                id,
                oilBaseCode,
                pricePerTon,
                region,
            })=>{

                console.log('oilBaseCode', oilBaseCode);
                console.log(OilDepotsCodesAndNamesMapping.find(({code})=>code==oilBaseCode)?.name ?? '');

                    return{
                        lotNumber:id,
                        fuelCode: fuelTypeCode,
                        OilDepotsRegion: OilDepotsCodesAndNamesMapping.find(({code})=>code==oilBaseCode)?.region ?? '',
                        fuelPriceByOneTon: pricePerTon,
                        fuelQuantity: availableBalance,
                        fuelName: FuelCodesAndNamesMapping.find(({code})=>code==fuelTypeCode)?.name ?? '',
                        OilDepotsName: OilDepotsCodesAndNamesMapping.find(({code})=>code==oilBaseCode)?.name ?? '',
                    }
                }))
        }
    },[data]);

    return (
        <>
            <Page className="page page_lots_list">
                <Form form={form}>
            <Flex gap={20}>
                <div style={{width:'50%'}}>
                    <Text className='title_list_name'>Список лотов</Text>
                    <Spacer space={20}/>
                            <TextField
                                isSearch={true}
                                isRequired={false}
                                name="search"
                                placeholder ='Поиск'
                                className="search_lots_list"
                            />
                    <Spacer space={20}/>
                    <List
                        pagination={{
                            position:'bottom',
                            align:'center',
                            total: totalElement,
                            onChange: onChangePagination
                        }}
                        dataSource={lotsList}
                        renderItem={({fuelPriceByOneTon, fuelQuantity, fuelName, lotNumber, OilDepotsName, OilDepotsRegion }, index) => (
                            <Card title={`Номер лота: ${lotNumber}`} key={lotNumber} className="lot_card">
                                <Flex vertical gap={5}>
                                    <Text className="fuel_card">Вид топлива: {fuelName} </Text>
                                    <Text className="name_card">Название нефтебазы: {OilDepotsName}</Text>
                                    <Text className="region_card">Регион нефтебазы: {OilDepotsRegion} </Text>
                                    <Text className="price_card">{fuelPriceByOneTon} ₽ за 1 тонну</Text>
                                    <Text className="remains_card">Осталось {fuelQuantity} тонн</Text>
                                    <Flex justify="end">
                                        <Button onClick={()=>goToLotPage(lotNumber)} type='primary' className="button_card">Подробнее</Button>
                                    </Flex>
                                </Flex>
                            </Card>)}
                    />
                </div>
            <div style={{width:'50%', paddingLeft:10}}>
                <div style={{position:'fixed'}}>
                    <Text className='title_filter_name'>Фильтрация поиска</Text>
                    <Spacer space={20}/>
                    <Text className='subtitle_filter_name'>Вид топлива:</Text>
                    <Spacer space={14}/>

                    <Form.Item name='checkedFuelList'>
                        <Checkbox.Group style={{marginLeft:'2px'}}>
                            <Flex vertical gap={6}>
                                {FuelCodesAndNamesMapping.map(({code, name})=>(<Checkbox key={code} value={code}>{name}</Checkbox>))}
                            </Flex>
                        </Checkbox.Group>
                    </Form.Item>


                    <Spacer space={20}/>
                    <Text className='subtitle_filter_name'>Название нефтебазы:</Text>
                    <Spacer space={14}/>


                    <Form.Item name='checkedOilDepotsList'>
                        <Checkbox.Group style={{marginLeft:'2px'}}>
                            <Flex vertical gap={6}>
                                {OilDepotsCodesAndNamesMapping.map(({code, name})=>(<Checkbox key={code} value={code}>{name}</Checkbox>))}
                            </Flex>
                        </Checkbox.Group>
                    </Form.Item>


                </div>
            </div>
            </Flex>
                <Spacer space={60}/>
                </Form>
            </Page>
        </>
    );
};