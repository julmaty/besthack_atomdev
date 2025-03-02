"use client"
import React from 'react';
import { Row, Col } from 'antd';
import styles from './TwoColumnFieldList.module.css';
import Spacer from "@/components/Universal/Spacer/Spacer";

interface Field {
    label: string;
    value: string;
}

interface TwoColumnFieldListProps {
    fields: Field[];
}

const TwoColumnFieldList: React.FC<TwoColumnFieldListProps> = ({ fields }) => {
    const renderedFields = fields.map((field, index) => (
        <div key={index} className={styles.fieldPair}>
            <div className={styles.fieldLabel} style={{fontSize: "16px"}}>{field.label}</div>
            <Spacer space={3}></Spacer>
            <div className={styles.fieldValue} style={{fontSize: "16px"}}>{field.value}</div>
            <Spacer space={10}></Spacer>
        </div>
    ));

    const columnCount = 2;
    const columns = [];

    for (let i = 0; i < columnCount; i++) {
        columns.push(
            <Col span={12} key={i}>
                {renderedFields
                    .filter((_, index) => index % columnCount === i)
                    .map((field) => field)}
            </Col>
        );
    }

    return (

        <Row gutter={20}>
            {columns}
        </Row>
    );
};

export default TwoColumnFieldList;