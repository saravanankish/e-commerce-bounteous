import Navbar from "../component/Navbar";
import { Outlet } from 'react-router-dom';
import { useSelector } from 'react-redux';

const Page = () => {

    const loggedIn = useSelector(state => state.loggedIn)

    return (
        <>
            <Navbar showAuth={!loggedIn} />
            <div style={{ marginTop: '60px' }}>
                <Outlet />
            </div>
        </>
    )

}

export default Page;