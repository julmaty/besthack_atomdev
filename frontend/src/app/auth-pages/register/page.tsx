'use client'
import { Flex, Form, Space } from 'antd';
import {useCallback, useState, useMemo} from 'react';
import Link from 'next/link';
import TextField from '@/components/Universal/Form/TextField/TextField';
import Text from '@/components/Universal/Text/Text';
import { Button } from 'antd';
import {RegisterFormData} from "@/app/auth-pages/register/Types";
import {Templates} from "@/app/auth-pages/register/Templates";
import isHaveEmailErrors from "@/utils/isHaveEmailErrors";
import useAPI from '@/utils/useAPI/useAPI';
import {signIn} from "next-auth/react";
import noop from "lodash-es/noop";
import {useRouter} from "next/navigation";

const {
    IS_ERROR_EMAIL_TEXT,
    IS_ERROR_CONFIRM_PASSWORD_TEXT,
    IS_ERROR_STRONG_PASSWORD_TEXT,
    IS_HAVE_ACCOUNT,
    EMAIL_TEXT,
    PASSWORD_TEXT,
    CONFIRM_PASSWORD_TEXT,
    CREATE_ACCOUNT_TEXT,
    ENTER_LINK_TEXT,
    PASSWORD_RULES
} = Templates
export default function RegisterForm() {

    const router = useRouter();

    const [, RegisterUserCallback] = useAPI<any>({
        requestMethod:'post',
        isCallback:true,
        authRequired: false,
        APIController: 'auth',
        APIMethod: 'register',
    });

    const [form] = Form.useForm<RegisterFormData>();

    const {email, password, confirmPassword} = Form.useWatch(({email, password, confirmPassword})=>({email, password, confirmPassword}), form)?? {
        email:'',
        password:'',
        confirmPassword:'',
    };

    const isErrorEmail=useMemo(()=>{
        console.log(isHaveEmailErrors(email));
        return isHaveEmailErrors(email);
    },[email]);

    const isErrorPassword=useMemo(()=>{
        const hasMinLength = password?.length >= 8;
        const hasUpperLetter = /[A-ZА-Я]/.test(password);
        const hasLowerLetter = /[a-zа-я]/.test(password);
        const hasSpecialCharacter = /[#!$%&^*_+|=?,.\/\\]/.test(password);
        return !(hasMinLength && hasUpperLetter && hasUpperLetter && hasLowerLetter && hasSpecialCharacter);
    },[password]);

    const [isVisibleSupportTextPassword, setIsVisibleSupportTextPassword] = useState<boolean>(false);

    const handleInputFocus = useCallback(() => {
        setIsVisibleSupportTextPassword(true);
    },[setIsVisibleSupportTextPassword]);

    const handleInputBlur = useCallback(() => {
        setIsVisibleSupportTextPassword(false);
    },[setIsVisibleSupportTextPassword]);

    const onFinish = useCallback(async ({email, password, lastName, firstName, fatherName}: RegisterFormData) => {
        RegisterUserCallback({ requestBody:{
            username: email.toLowerCase(),
            password: password
            }}).then(()=>{
            signIn('credentials', {
                username:email.toLowerCase(),
                password:password,
                redirect: false,
            }).then(async (res) => {
                console.log('enter', res);
                if(res?.ok==true){
                    router.push("/lots-list");
                }else{
                    alert('Во время регистрации возникла ошибка');
                }
            }).catch(noop);
        }).catch((error)=>{
            //Ошибка
            alert('Во время регистрации возникла ошибка');
            console.log('error', error);
        });

        //Осуществляем вход в систему

    },[RegisterUserCallback, router]);

    return(
        <>
            <Form className="register_form" onFinish={onFinish} layout='vertical' form={form}>
                <Flex vertical gap={10}>
                    <Text className="title_auth">
                        Регистрация
                    </Text>
                    <TextField
                        errorText={IS_ERROR_EMAIL_TEXT}
                        name='email'
                        label={EMAIL_TEXT}
                        isError={isErrorEmail}
                    />
                    <TextField
                        errorText={IS_ERROR_STRONG_PASSWORD_TEXT}
                        name='password'
                        label={PASSWORD_TEXT}
                        isError={isErrorPassword}
                        onFocus={handleInputFocus}
                        onBlur={handleInputBlur}
                        isPassword
                    />
                    <Space style={{overflow: 'hidden', height: isVisibleSupportTextPassword ? 115 : 0, transition: 'height 0.5s ease-in-out'}}>
                        <Text className="condition_password">
                            {PASSWORD_RULES}
                        </Text>
                    </Space>
                    <TextField
                        errorText={IS_ERROR_CONFIRM_PASSWORD_TEXT}
                        name='confirmPassword'
                        label={CONFIRM_PASSWORD_TEXT}
                        isError={confirmPassword!=password}
                        isPassword
                    />
                    <Button
                        type='primary'
                        htmlType='submit'
                        size='large'
                    >{CREATE_ACCOUNT_TEXT}</Button>

                    <Flex gap={10}>
                        <Text className="text_strong">
                            {IS_HAVE_ACCOUNT}
                        </Text>

                        <Link href='/auth-pages/login' className="link_strong">
                            {ENTER_LINK_TEXT}
                        </Link>
                    </Flex>
                </Flex>
            </Form>
        </>
    )
};