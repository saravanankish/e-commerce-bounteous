import { useEffect, useState } from "react";
import { backendUrl } from "../config";
import axios from 'axios';

const useProductRender = (pageNumber) => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [errors, setErrors] = useState(false);
    const [hasMore, setHasMore] = useState(false);

    useEffect(() => {
        setLoading(true);
        setErrors(false);
        let cancel;
        axios({
            method: "GET",
            url: `${backendUrl}/products`,
            params: { page: pageNumber },
            cancelToken: new axios.CancelToken(c => cancel = c)
        }).then(res => {
            if (res.status === 200) {
                setProducts(prev => {
                    return [...prev, ...res.data.products]
                })
                setHasMore(res.data.products.length > 0)
                setLoading(false)
            }
        }).catch(err => {
            if (axios.isCancel(err)) return;
            setErrors(true);
        })
        return () => cancel();
    }, [pageNumber])

    return { loading, errors, products, hasMore };

}

export default useProductRender;
