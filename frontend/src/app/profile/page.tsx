'use client';
import Page from '@/components/Page/Page';
import Text from "@/components/Universal/Text/Text";
import React, {useState} from "react";
import { Flex } from 'antd';
import { Image } from "antd";
import Spacer from "@/components/Universal/Spacer/Spacer";
import "@/styles/global.css"

import TwoColumnFieldList from "@/app/profile/components/TwoColumnFieldList/TwoColumnFieldList";

interface UserDataType {
    key?: React.Key,
    fullName: string,
    id?: number,
    titleCompany?: string,
    activityCompany?: string,
    INNCustomer?: string,
    mailCustomer?: string,
    descriptionCompany?: string,
    usersId?: number,
    phone?: string,
}

export default function Profile() {

    const [user, setUser] = useState<UserDataType>({
        id: 1,
        fullName: 'Снейп Северус Тобиас',
        titleCompany: 'АО "Что-то там"',
        INNCustomer: '2281337',
        phone:'89218464989',
        mailCustomer: "olegood@mail.ru" ,
        descriptionCompany: "Нефтяные организации осуществляют все работы, связанные с добычей углеводородов. В списке самых крупных предприятий России большая часть – нефтяные компании." ,
        activityCompany: "Отрасль экономики, занимающаяся добычей, переработкой"
    })

    const fields = [
        { label: 'Почта', value: `${user.mailCustomer}` },
        { label: 'Название компании', value: `${user.titleCompany}` },
        { label: 'Телефон', value:`${user.phone}` },
        { label: 'Должность', value: 'Ведущий менеджер' },
        { label: 'ИНН', value: `${user.INNCustomer}` },
    ];

    return (
        <div style={{
            padding: 35,
            fontSize: "16px",

        }}>
        <Page>
            <Flex gap={25}  >
            <Image
                style={{
                    width: '250px',
                    height: '250px',
                    borderRadius: '20px',
                    objectFit: 'cover',

                }}
                preview={false}
                src={"https://static.wikia.nocookie.net/harrypotter/images/a/a3/Severus_Snape.jpg"}/>

                <Flex vertical>
                    <Spacer space={25}></Spacer>
                    <Text style={{
                        fontSize: "32px",
                        fontWeight: 700,
                    }}> {user.fullName} </Text>
                    <Spacer space={30}></Spacer>
                    <TwoColumnFieldList fields={fields}></TwoColumnFieldList>
                </Flex>
            </Flex>

            <Flex vertical gap={2} style={{lineHeight: 1.5}}>
                <Spacer space={20}></Spacer>
            <Text style={{color: "#999"}}>Вид деятельности компании</Text>
                <Text  style={{maxWidth: "800px"}}>{user?.activityCompany}</Text>
                <Spacer space={20}></Spacer>
                <Text style={{color: "#999"}}>Описание компании </Text>
                <Text style={{
                    maxWidth: "800px"}}> {user?.descriptionCompany}</Text>
        </Flex>
        </Page>
        </div>
    );
};
