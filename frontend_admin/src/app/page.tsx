'use client';

import { UploadOutlined } from '@ant-design/icons';
import { Button } from 'antd';

export default function App(){
    return (
        <>
            <div>
                <div style={{marginTop: '50px', marginLeft: '50px'}}>
                    <form
                        action="http://51.250.70.37/api/lots/upload"
                        encType="multipart/form-data"
                        method="post"
                    >
                        <input name="file" type="file" multiple/>
                        <Button htmlType="submit" icon={<UploadOutlined/>}>Загрузить файл</Button>
                    </form>
                </div>
            </div>
        </>
    )
}