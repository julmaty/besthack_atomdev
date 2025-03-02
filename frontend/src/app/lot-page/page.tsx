'use client';
import './lot-page.css';
import Page from '@/components/Page/Page';
import Text from "@/components/Universal/Text/Text";
import { Flex } from 'antd';
import { Spin } from 'antd';
import { Button } from 'antd';
import {useCallback, useEffect, useMemo, useState} from "react";
import dayjs from "dayjs";
import { FuelCodesAndNamesMapping, OilDepotsCodesAndNamesMapping } from "@/utils/Templates";
import Spacer from "@/components/Universal/Spacer/Spacer";
import CreateOrderModal from "@/app/lot-page/components/CreateOrderModal/CreateOrderModal";
import Image from "next/image";
import EnterLogo from "@/enter-logo.svg";
import useAPI from "@/utils/useAPI/useAPI";
import {isNil} from "lodash-es";
import {useRouter, useSearchParams} from 'next/navigation'
import customParseFormat from "dayjs/plugin/customParseFormat";
import Link from "next/link";
dayjs.extend(customParseFormat);


export default function LotPage() {

    const router=useRouter();

    const searchParams = useSearchParams();

    const lotNumber = searchParams.get('lotNumber');

    const [{data, isLoading, error}] = useAPI<any>({
        APIController: 'api',
        APIMethod: `lots/${lotNumber}`,
    });

    useEffect(() => {
        if(!isNil(error) || isNil(lotNumber)){
            router.replace(`/error`);
        }
    },[error]);

    const lot=useMemo(()=>{

        if (!isNil(data)){

            console.log('data', data);

            const {availableBalance, fuelTypeCode, id, oilBaseCode, pricePerTon, status, startWeight, lotDate, availableBalanceByOilBase}=data;
             return {
                status: status,
                lotNumber: id,
                OilDepotsCode: oilBaseCode,
                fuelCode: fuelTypeCode,
                createdDate: dayjs(lotDate, "YYYY-DD-MM"),
                startingWeight: startWeight,
                availableBalanceByOilBase:availableBalanceByOilBase,
                fuelPriceByOneTon: pricePerTon,
                fuelQuantity: availableBalance,
            }
        }
    },[data]);


    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal=useCallback(()=>{
        setIsModalOpen(true);
    },[setIsModalOpen]);

    const closeModal=useCallback(()=>{
        setIsModalOpen(false);
    },[setIsModalOpen]);

    const handleCancel=useCallback(()=>{closeModal()},[closeModal]);

    const [, createOrderCallback] = useAPI<any>({
        requestMethod:'post',
        isCallback:true,
        authRequired: false,
        APIController: 'api',
        APIMethod: 'orders',
    });


    const ModalFinish=useCallback(async ({tonQuantity, deliveryType}:any)=>{
        console.log('tonQuantity', tonQuantity);
        await createOrderCallback({ requestBody:{
                lotId: lotNumber,
                volume: tonQuantity,
                deliveryType: deliveryType,
            }}).then(()=>{
            closeModal();
            router.push('/lots-list');
        }).catch(()=>{
            alert('Требуемое количество топлива недоступно');
            closeModal();
            router.push('/lots-list');
        });
    },[router, createOrderCallback]);

    return (
        <>
            <Page className="page lot_page">
                { isLoading || isNil(data) ? (
                    <Flex justify='center' style={{marginTop:'40px'}}>
                        <Spin size="large" />
                    </Flex>
                ):(<Flex style={{marginLeft: '20px', marginTop: '10px'}}>

                    <div style={{width: '50%'}}>
                        <Flex vertical gap={10}>
                            <Button style={{width:'200px'}}><Link href="/lots-list">Назад</Link></Button>
                            <Text className="page_status">Статус: {lot?.status}</Text>
                            <Text className="page_lot">Лот: {lot?.lotNumber}</Text>
                            <Text className="page_neft">Нефтебаза: {OilDepotsCodesAndNamesMapping.find(({code}) => code === lot?.OilDepotsCode)?.name ?? ''} </Text>
                            <Text className="page_fuel">Вид топлива: {FuelCodesAndNamesMapping.find(({code}) => code === lot?.fuelCode)?.name ?? ''} </Text>
                            <Text className="page_create">Создан: {lot?.createdDate.format('DD.MM.YYYY')}</Text>
                            <Text className="page_create">Код КССС НБ: {lot?.OilDepotsCode} </Text>
                            <Text className="page_create">Остаток топлива данного вида на нефтебазе: {lot?.availableBalanceByOilBase} </Text>
                            <Text className="page_create">Код КССС Топлива: {lot?.fuelCode} </Text>
                            <Text className="page_create">Стартовый вес: {lot?.startingWeight} тонн</Text>
                            <Text className="page_create">Остаток топлива: {lot?.fuelQuantity} тонн</Text>
                            <Text className="page_create">Цена лота: {lot?.fuelQuantity * lot?.fuelPriceByOneTon} ₽</Text>
                            <Text className="page_priceForOne">Цена лота: {lot?.fuelPriceByOneTon} ₽ за 1 тонну</Text>
                            <Spacer space={1}/>
                            <div>
                                <Button type='primary' onClick={openModal}>Оформить заказ</Button>
                            </div>
                        </Flex>
                    </div>

                    <Flex style={{width:'50%', paddingLeft:10, position:'fixed', right:0, pointerEvents:'none'}} justify='center'>
                        <Image
                            src={EnterLogo}
                            alt={'logo'}
                            style={{width:'70%',height:'70%'}}
                        />
                    </Flex>

                </Flex>)}
            </Page>
            <CreateOrderModal
                isModalOpen={isModalOpen}
                handleCancel={handleCancel}
                ModalFinish={ModalFinish}
                fuelQuantity={lot?.fuelQuantity}
                fuelPriceByOneTon={lot?.fuelPriceByOneTon}
            />
        </>
    );
};