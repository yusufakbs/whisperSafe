import React from 'react'
import { useNavigate } from 'react-router-dom'

const StatusUserCard = () => {

    const navigate = useNavigate();
    const handleNavigate = () => {
        navigate(`/status/{userId}`)
    }

    return (
        <div onClick={handleNavigate} className='flex items-center p-3 cursor-pointer'>
            <div>
                <img className='h-7 w-7 lg:w-10 lg:h-10 rounded-full' src="https://cdn.pixabay.com/photo/2024/02/15/15/46/cat-8575641_960_720.jpg" alt="" />
            </div>
            <div className='ml-2 text-white'>
                <p>Pablo Escobar</p>
            </div>
        </div>
    )
}

export default StatusUserCard