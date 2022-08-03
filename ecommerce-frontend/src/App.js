import './App.css';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Home from './page/Home';
import Register from './page/Register';
import store from './redux/store';
import Page from './page/Page';
import { Provider } from 'react-redux'

function App() {

  return (
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route element={<Page />} path="/" >
            <Route element={<Home />} index />
            <Route element={<Register />} path="/register" />
          </Route>
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </BrowserRouter>
    </Provider>
  );
}

export default App;
