"use client"
import "./Header.css";
import {useCallback, useEffect, useMemo} from "react";
import Link from "next/link";
import { Button } from "antd";
import Text from "@/components/Universal/Text/Text";
import {signOut, useSession} from "next-auth/react";
import noop from "lodash-es/noop";
import Image from 'next/image';
import HeaderLogo from "../../header-logo.svg";
import {Flex} from "antd"
import ConditionalRender from "@/components/Universal/ConditionalRender/ConditionalRender";
import {usePathname, useRouter} from 'next/navigation'
import {NextResponse} from "next/server";

export default function Header() {
    const { data: session } = useSession();
    const exit = useCallback(() => {
        signOut({callbackUrl: "/auth-pages/login"}).catch(noop);
     },[]);
    const router=useRouter();

    console.log('session frontend', session);

    const pathname = usePathname();

    console.log('pathname', pathname);

    const existHeaderInPage=useMemo(()=>{
        return !(pathname == '/auth-pages/login' || pathname == '/auth-pages/register');
    },[pathname]);

    return (
        <ConditionalRender condition={session!=null && existHeaderInPage}>
        <div className='header'>
            <div className='content'>

                <Flex gap={16}>
                    <Image
                        width={50}
                        height={50}
                        src={HeaderLogo}
                        alt={'logo'}
                    />
                    <Text className='siteTitle'>Топливный <br/> Резерв</Text>
                </Flex>
                <Flex gap={10}>
                    <Button><Link href="/lots-list">Список лотов</Link></Button>
                    <Button><Link href="/order-history">История заказов</Link></Button>
                    <Button><Link href="/profile">Профиль</Link></Button>
                    <Button onClick={exit}>Выйти</Button>
                </Flex>
            </div>
        </div>
        </ConditionalRender>
    )
}