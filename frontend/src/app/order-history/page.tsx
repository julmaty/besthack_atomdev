'use client';
import './order-history.css';
import Page from '@/components/Page/Page';
import Text from "@/components/Universal/Text/Text";
import { useEffect,  useState} from "react";
import { Flex,  List, Card } from 'antd';
import Spacer from "@/components/Universal/Spacer/Spacer";
import Image from "next/image";
import EnterLogo from "@/enter-logo.svg";
import useAPI from "@/utils/useAPI/useAPI";
import {isNil} from "lodash-es";
import customParseFormat from "dayjs/plugin/customParseFormat";
import dayjs from "dayjs";
import {deliveryMappingToText, FuelCodesAndNamesMapping, OilDepotsCodesAndNamesMapping} from "@/utils/Templates";
dayjs.extend(customParseFormat);

export default function OrdersList() {

    const [{data}] = useAPI<any>({
        APIController: 'api',
        APIMethod: 'orders',
    });

    const [orderList, setOrdersList]=useState([]);

    useEffect(()=>{

        console.log('data order history', data);

        if(!isNil(data)){
            setOrdersList(data.map(({lot, deliveryType, id, orderDate, volume})=>{
                return{
                    orderDate:dayjs(orderDate, "YYYY-DD-MM").format('DD.MM.YYYY'),
                    orderId:id,
                    lotNumber:lot.id,
                     volume: volume,
                    deliveryType:deliveryType,
                    fuelName: FuelCodesAndNamesMapping.find(({code})=>code===lot.fuelTypeCode)?.name ?? '',
                    OilDepotsName: OilDepotsCodesAndNamesMapping.find(({code})=>code===lot.oilBaseCode)?.name ?? '',
                }
            }))
        }
    },[data]);

    return (
        <>
            <Page className="page page_order_history">
            <Flex gap={20}>
                <div style={{width:'50%'}}>
                    <Text className='title_list_name'>История заказов</Text>
                    <Spacer space={20}/>
                    <List dataSource={orderList} renderItem={({orderId, lotNumber, deliveryType, fuelName, OilDepotsName, volume, orderDate}, index) => (
                            <Card title={`Номер заказа: ${orderId}`} key={orderId} className="lot_card">
                                <Flex vertical gap={5}>
                                    <Text className="fuel_card">Дата создания заказа: {orderDate} </Text>
                                     <Text className="fuel_card">Вид топлива: {fuelName} </Text>
                                     <Text className="fuel_card">Тип доставки: {deliveryType} </Text>
                                    <Text className="number_card">Номер лота: {lotNumber} </Text>
                                    <Text className="region_card">Регион нефтебазы: {OilDepotsName} </Text>
                                    <Text className="remains_card">Заказано {volume} тонн</Text>
                                </Flex>
                            </Card>
                        )}
                    />
                </div>
                <Flex style={{width:'50%', paddingLeft:10, paddingTop:32, position:'fixed', right:0, pointerEvents:'none'}} justify='center'>
                    <Image
                        src={EnterLogo}
                        alt={'logo'}
                        style={{width:'80%',height:'80%'}}
                    />
                </Flex>
            </Flex>
                <Spacer space={60}/>
            </Page>
        </>
    );
};