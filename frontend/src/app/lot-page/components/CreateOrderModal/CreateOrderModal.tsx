'use client';
import { Modal, Form } from "antd";
import { Button } from 'antd';
import { Flex } from 'antd';
import Text from "@/components/Universal/Text/Text";
import TextField from "@/components/Universal/Form/TextField/TextField";
import Spacer from "@/components/Universal/Spacer/Spacer";
import {useCallback, useMemo} from "react";
import {isNil} from "lodash-es";
import ConditionalRender from "@/components/Universal/ConditionalRender/ConditionalRender";
import {Select} from "antd";

export default function CreateOrderModal({title, ModalFinish, isModalOpen, handleCancel, fuelQuantity, fuelPriceByOneTon}:any) {
    const [form] = Form.useForm();

    const { tonQuantity } = Form.useWatch(({ tonQuantity })=>({ tonQuantity }), form)?? {
        tonQuantity:'',
    };

    const isErrorTonQuantity=useMemo(() => {

        const isNumber=/^\d+$/.test(tonQuantity);

        if(!isNumber){
            return true;
        }

        return Number.parseInt(tonQuantity)>fuelQuantity;

    },[tonQuantity]);

    const errorText=useMemo(()=>{

        if(!/^\d+$/.test(tonQuantity)){
            return 'Поле должно быть заполнено числом';
        }

        if(Number.parseInt(tonQuantity)>fuelQuantity){
            return 'Вы не можете заказать больше топлива, чем его есть в остатке';
        }

    },[tonQuantity, fuelQuantity]);

    const price=useMemo(()=>{
        if(isErrorTonQuantity){

            return null;
        }

        return Number.parseInt(tonQuantity)*fuelPriceByOneTon;
    },[isErrorTonQuantity, tonQuantity, fuelPriceByOneTon]);

    const handleCancelModal=useCallback(()=>{
        form.resetFields();
        handleCancel();
    },[handleCancel]);

    return (
        <Modal
            title={title}
            open={isModalOpen}
            onCancel={handleCancelModal}
            footer={null}
            width={600}
            destroyOnClose={true}
        >
                <Form form={form} onFinish={ModalFinish} layout='vertical'>
                    <Flex className="modal_create_order" vertical>
                        <TextField
                            label="Количество топлива в тоннах"
                            name='tonQuantity'
                            size='middle'
                            errorText={errorText}
                            isError={isErrorTonQuantity}
                        />

                        <ConditionalRender condition={!isNil(price)}>
                            <Flex vertical gap={1}>
                                <Text>Итоговая сумма</Text>
                                <Text style={{fontSize: "20px", fontWeight: "500"}}>{price} рублей</Text>
                            </Flex>
                        </ConditionalRender>

                        <Form.Item name='deliveryType' label='Выберите тип доставки' rules={[{required: true, message: 'Выберите тип доставки'}]}>
                            <Select options={[{value:'Самовывоз', label:'Самовывоз'}, {value:'Доставка', label:'Доставка'}]}/>
                        </Form.Item>

                        <Spacer space={10}/>
                            <Flex gap={20} justify="end">
                                <Button onClick={handleCancelModal}>Отменить</Button>
                                <Button type="primary" htmlType="submit">Оплатить</Button>
                            </Flex>
                    </Flex>
                </Form>
        </Modal>
    );
}