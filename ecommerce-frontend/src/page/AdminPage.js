import AdminNavbar from "../component/AdminNavbar";
import { Outlet } from 'react-router-dom';
import Footer from '../component/Footer';

const AdminPage = () => {

    return (
        <>
            <AdminNavbar />
            <div style={{ marginTop: '45px' }}>
                <Outlet />
            </div>
            <Footer />
        </>
    )
}

export default AdminPage;