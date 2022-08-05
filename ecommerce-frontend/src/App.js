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
import ProductsView from './page/ProductsView';

function App() {

  const [role, setRole] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    if (sessionStorage.getItem("token")) {
      setRole(JSON.parse(sessionStorage.getItem("token")).role)
      setLoggedIn(sessionStorage.getItem("token") !== null && sessionStorage.getItem("token") !== undefined)
    } else {
      if (window.location.href !== "http://127.0.0.1:3000/" && window.location.href !== "http://127.0.0.1:3000/register")
        window.location.replace("http://127.0.0.1:3000")
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
                  <Route path="add">
                    <Route path="product" element={<AddProduct />} />
                    <Route path="customer" element={<AddCustomer type="Add" />} />
                  </Route>
                  <Route path="edit">
                    <Route path="customer/:customerId" element={<AddCustomer type="Update" edit={true} />} />
                  </Route>
                  <Route path="customers" element={<ViewCustomer />} />
                  <Route path="products" element={<ProductsView />} />
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
