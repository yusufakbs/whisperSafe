import React, { useState } from 'react'
import { AiOutlineSearch } from 'react-icons/ai'
import { BiCommentDetail } from 'react-icons/bi'
import { TbCircleDashed } from 'react-icons/tb'
import { BsFilter } from 'react-icons/bs'
import ChatCard from './ChatCard/ChatCard'

const HomePage = () => {

  const [querys, setQuerys] = useState(null);
  const handleSearch = () => [

  ]


  return (
    <div className='relative'>
      <div className='w-full py-14 bg-[#060a09]'></div>
      <div className='flex bg-[#f0f2f5] h-[90vh] absolute top-6 left-6 w-full'>
        <div className='left w-[30%] bg-[#e8e9ec] h-full '>

          <div className='w-full'>
            <div className='flex justify-between items-center p-3'>
              <div className='flex items-center space-x-3'>
                <img className='rounded-full w-10 h-10 cursor-pointer'
                  src="https://cdn.pixabay.com/photo/2024/03/04/16/38/cat-8612685_960_720.jpg" alt="" />
                <p>Username</p>
              </div>
              <div className='space-x-3 text-2xl flex'>
                <TbCircleDashed />
                <BiCommentDetail />
              </div>
            </div>

            <div className='relative flex justify-center items-center bg-white py-4 px-3'>
              <input className='border-none outline-none bg-slate-200 rounded-md w-[93%] pl-9 py-2' type='text'
                placeholder='Search or start new Chat'
                onChange={(e) => {
                  setQuerys(e.target.value)
                  handleSearch(e.target.value)
                }}
                value={querys}
              />
              <AiOutlineSearch className='left-5 top-7 absolute ' />
              <div>
                <BsFilter className='ml-4 text-3xl' />
              </div>
            </div>
            {/* all user*/}
            <div className='bg-white overflow-y-scroll h-[76.8vh] px-2'>
              {querys && [1, 1, 1, 1, 1].map((item) => <div> <hr /> <ChatCard /> </div>)}
            </div>

            {/* Default Page */}
            <div>

            </div>

          </div>
        </div>
        <div className='right'>
          <div className='w-[70%] flex flex-col items-center justify-center h-full w-full'>
            <div className='w-full  text-center '>
              <h1 className='text-4xl text-gray-600'>WhispareSafe</h1>
              <p className='my-9'>Welcome to WhispareSafe! Stay connected, send and receive your messages from anywhere..</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default HomePage