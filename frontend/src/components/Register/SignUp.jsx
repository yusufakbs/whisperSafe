import { Alert, Button, Snackbar } from '@mui/material';
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { Navigate, useNavigate } from 'react-router-dom';
import { currentUser, register } from '../../redux/Auth/Action.js'

const SignUp = () => {

    const [openSnackBar, setOpenSnackBar] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { auth } = useSelector(store => store);
    const token = localStorage.getItem("token");
    console.log('current user', auth.reqUser)
    const [inputData, setInputData] = useState({ username: "", email: "", password: "" });
    const handleSubmit = (e) => {
        e.preventDefault()
        console.log("handle submit", inputData)
        dispatch(register(inputData))
        setOpenSnackBar(true)
    }
    const handleChange = (e) => {
        const { name, value } = e.target;
        setInputData((values) => ({ ...values, [name]: value }));
    }
    const handleSnackBarClose = () => {
        setOpenSnackBar(false);
    }

    useEffect(() => {
        if (token) dispatch(currentUser(token));
    }, [token])

    useEffect(() => {
        if (auth.reqUser?.username) {
            navigate("/")
        }
    })

    return (
        <div>
            <div>
                <div className='flex justify-center h-screen items-center bg-gray-100'>
                    <div className='w-[30%] p-10 shadow-md bg-white'>
                        <form onSubmit={handleSubmit} className='space-y-5'>
                            <div>
                                <p className='mb-2'>Username</p>
                                <input placeholder='Enter your Username' name='username' onChange={(e) => handleChange(e)} value={inputData.username} type="text" className='py-2 outline outline-gray-600 w-full rounded-md border' />
                            </div>
                            <div>
                                <p className='mb-2'>Email</p>
                                <input placeholder='Enter your Email' name='email' onChange={(e) => handleChange(e)} value={inputData.email} type="text" className='py-2 outline outline-gray-600 w-full rounded-md border' />
                            </div>
                            <div>
                                <p className='mb-2'>Password</p>
                                <input placeholder='Enter your password' name='password' onChange={(e) => handleChange(e)} value={inputData.password} type="text" className='py-2 outline outline-gray-600 w-full rounded-md border' />
                            </div>
                            <div>
                                <Button type='submit' sx={{ bgcolor: "gray", padding: ".5rem 0rem" }} className='w-full' variant='contained'>Sign Up</Button>
                            </div>
                        </form>
                        <div className='flex space-x-3 items-center mt-5'>
                            <p className='m-0'>Already have account ?</p>
                            <Button variant='text' onClick={() => navigate("/signin")}>Sign In</Button>
                        </div>
                    </div>
                </div>

                <Snackbar open={openSnackBar} autoHideDuration={6000} onClose={handleSnackBarClose}>
                    <Alert onClose={handleSnackBarClose} severity='success' sx={{ width: '100%' }}> Your Account Successfully Created! </Alert>
                </Snackbar>

            </div>
        </div>
    )
}

export default SignUp