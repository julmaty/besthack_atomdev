'use client'

import './auth.css';
import { Flex } from 'antd';
import {PropsWithChildren} from "react";
import Image from "next/image";
import EnterLogo from "@/enter-logo.svg";

export default function AuthLayout({ children }: PropsWithChildren) {
    return (
            <Flex className="auth_pages " justify='space-evenly' align="center">
                <Image
                    src={EnterLogo}
                    alt={'logo'}
                    style={{width:'45%',height:'45%'}}
                />
                <div className='auth_container'>
                {children}
                </div>
            </Flex>
    );
}
