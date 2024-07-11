import React, { useState } from 'react'
import { BsArrowLeft, BsCheck2, BsPencil } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom'

const Profile = ({ handleCloseOpenProfile }) => {

    const [flag, setFlag] = useState(false);
    const handleFlag = () => {
        setFlag(true);
    }
    const navigate = useNavigate();
    const [username, setUsername] = useState(null);
    const handleCheckClick = () => {
        setFlag(false);
    }
    const handleChange = (e) => {
        setUsername(e.target.value);
        console.log(username);
    }

    return (
        <div className='w-full h-full'>
            <div className='flex items-center space-x-10 bg-gray-300 text-white pt-16 px-10 pb-5'>
                <BsArrowLeft className='cursor-pointer text-2xl font-bold' onClick={handleCloseOpenProfile} />
                <p className='cursor-pointer font-semibold'>Profile</p>
            </div>

            {/** update profile pic section  */}
            <div className='flex flex-col justify-center items-center my-12'>
                <label htmlFor="imgInput"> <img className='rounded-full w-[15vw] h-[15vw] cursor-pointer' src="https://cdn.pixabay.com/photo/2023/05/27/22/56/kitten-8022452_960_720.jpg" alt="" /></label>
                <input type="file" id='imgInput' className='hidden' />
            </div>

            {/**Name section */}
            <div className='bg-white px-3'>
                <p className='py-3'>Your Name</p>
                {
                    !flag && <div className='w-full flex justify-between items-center'>
                        <p className='py-3'>{username || "username"}</p>
                        <BsPencil onClick={handleFlag} className='cursor-pointer' />
                    </div>
                }
                
                {
                    flag && <div className='w-full flex justify-between items-center py-2'>
                        <input onChange={handleChange} className='w-[80%] outline-none border-b-2 border-gray-700 p-2' type="text" placeholder='Enter your name' />
                        <BsCheck2 onClick={handleCheckClick} className='cursor-pointer text-2xl' />
                    </div>
                }
            </div>

            <div className='px-3 my-5'>
                <p className='py-10'>This is not your username, this name will be visible to your WhispareSafe contects.</p>
            </div>

        </div>
    )
}

export default Profile