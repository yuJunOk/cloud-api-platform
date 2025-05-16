import {Layout} from 'antd';
const { Footer } = Layout;

export const GlobalFooter = () => {
    return (
        <Footer style={{ textAlign: 'center', backgroundColor: '#fff' }}>
            Ant Design ©{new Date().getFullYear()} Created by Ant UED
        </Footer>
    );
};