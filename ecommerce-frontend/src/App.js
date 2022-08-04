import './App.css';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Home from './page/Home';
import Register from './page/Register';
import store from './redux/store';
import Page from './page/Page';
import { Provider } from 'react-redux'
import { SnackbarProvider } from 'notistack';
import { useEffect, useState } from 'react';
import AdminPage from './page/AdminPage';
import ViewCustomer from './page/ViewCustomer';
import AddProduct from './page/AddProduct';
import AddCustomer from './page/AddCustomer';

function App() {

  const [role, setRole] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    if (sessionStorage.getItem("token")) {
      setRole(JSON.parse(sessionStorage.getItem("token")).role)
      setLoggedIn(sessionStorage.getItem("token") !== null && sessionStorage.getItem("token") !== undefined)
    }
    // eslint-disable-next-line
  }, [sessionStorage.getItem("token")])

  return (
    <Provider store={store}>
      <SnackbarProvider maxSnack={1} anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'center',
      }}>
        <BrowserRouter>
          <Routes>
            {
              (role === "" || role === "CUSTOMER") &&
              <Route element={<Home />} index />
            }
            {
              role === "CUSTOMER" || role === "" ?
                <Route element={<Page />} path="/" >
                  {
                    !loggedIn &&
                    <Route element={<Register />} path="/register" />
                  }
                </Route>
                :
                <Route path="/admin" element={<AdminPage />} >
                  <Route path="add-product" element={<AddProduct />} />
                  <Route path="add-customer" element={<AddCustomer type="Add" />} />
                  <Route path="customers" element={<ViewCustomer />} />
                </Route>
            }
            {
              role &&
              <Route path="*" element={role === "CUSTOMER" || role === "" ? <Navigate to="/" /> : <Navigate to="/admin" />} />
            }
          </Routes>
        </BrowserRouter>
      </SnackbarProvider>
    </Provider>
  );
}

export default App;
