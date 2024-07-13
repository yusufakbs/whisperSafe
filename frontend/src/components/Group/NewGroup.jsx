import { Button, CircularProgress } from '@mui/material'
import React, { useState } from 'react'
import { BsArrowLeft, BsCheck2 } from 'react-icons/bs'

const NewGroup = () => {
    const [isImageUploading, setIsImageUploading] = useState(false);
    const [groupName, setGroupName] = useState();
    return (
        <div className='w-full h-full'>
            <div className='flex items-center space-x-10 bg-[#878888] text-white pt-16 px-10 pb-5'>
                <BsArrowLeft className='cursor-pointer text-2xl font-bold' />
                <p className='text-xl font-semibold'>New Group</p>
            </div>
            <div className='flex flex-col justify-center items-center my-12'>
                <label htmlFor="ImgInput" className='relative'>
                    <img className='rounded-full w-[15vw] h-[15vw] cursor-pointer' src="https://cdn.pixabay.com/photo/2024/02/17/15/14/crows-8579540_960_720.jpg" alt="" />
                    {isImageUploading && <CircularProgress className='absolute top-[5rem] left-[6rem]' />}
                </label>
                <input type="file" id='ImgInput' className='hidden' onChange={() => console.log("imageonChange")} />
            </div>
            <div className='w-full flex justify-between items-center py-2 px-5'>
                <input className='w-full outline-none border-b-2 border-gray-700 px-2 bg-transparent' placeholder="Group Subject" value={groupName} type="text" onChange={(e) => setGroupName(e.target.value)} />

            </div>

            {groupName && <div className='py-10 flex items-center justify-center' >
                <Button>
                    <div className='bg-[#706e6e] rounded-full p-4 '>
                        <BsCheck2 className='text-white font-bold text-3xl'/>
                    </div>
                </Button>
            </div>
            }

        </div>
    )
}

export default NewGroup