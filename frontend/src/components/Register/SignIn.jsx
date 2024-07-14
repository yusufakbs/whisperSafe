import { Alert, Button, Snackbar } from '@mui/material';
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { Navigate, useNavigate } from 'react-router-dom';
import { currentUser, login } from '../../redux/Auth/Action';

const SignIn = () => {
    const [openSnackBar, setOpenSnackBar] = useState(false);
    const navigate = useNavigate();
    const [inputData, setInputData] = useState({ email: "", password: "" });
    const dispatch = useDispatch();
    const { auth } = useSelector(store => store);
    const token = localStorage.getItem("token")
    const handleSubmit = (e) => {
        e.preventDefault()
        console.log("handle submit", inputData)
        setOpenSnackBar(true)
        dispatch(login(inputData))

    }
    const handleChange = (e) => {
        const { name, value } = e.target;
        setInputData((values) => ({ ...values, [name]: value }))

    }

    const handleSnackBarClose = () => {
        setOpenSnackBar(false);
    }

    useEffect(() => {
        if (token) dispatch(currentUser(token));
    }, [token]);

    useEffect(() => {
        if (auth.reqUser?.email) {
            navigate("/")
        }
    }, [auth.reqUser]);

    return (
        <div>
            <div className='flex justify-center h-screen items-center bg-gray-100'>
                <div className='w-[30%] p-10 shadow-md bg-white'>
                    <form onSubmit={handleSubmit} className='space-y-5'>
                        <div>
                            <p className='mb-2'>Email</p>
                            <input placeholder='Enter your Email' name='email' onChange={handleChange} value={inputData.email} type="text" className='py-2 outline outline-gray-600 w-full rounded-md border' />
                        </div>
                        <div>
                            <p className='mb-2'>Password</p>
                            <input placeholder='Enter your password' name='password' onChange={handleChange} value={inputData.password} type="text" className='py-2 outline outline-gray-600 w-full rounded-md border' />
                        </div>
                        <div>
                            <Button type='submit' sx={{ bgcolor: "gray", padding: ".5rem 0rem" }} className='w-full' variant='contained'>Sign In</Button>
                        </div>
                    </form>
                    <div className='flex space-x-3 items-center mt-5'>
                        <p className='m-0'>Create New Account</p>
                        <Button variant='text' onClick={() => navigate("/signup")}>Sign Up</Button>
                    </div>
                </div>
            </div>

            <Snackbar open={openSnackBar} autoHideDuration={6000} onClose={handleSnackBarClose}>
                <Alert onClose={handleSnackBarClose} severity='success' sx={{ width: '100%' }}> Login Successfull! </Alert>
            </Snackbar>

        </div>
    )
}

export default SignIn