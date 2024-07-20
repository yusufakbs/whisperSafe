import { Button, CircularProgress } from '@mui/material'
import React, { useState } from 'react'
import { BsArrowLeft, BsCheck2 } from 'react-icons/bs'
import { useDispatch } from 'react-redux';
import CreateGroup from './CreateGroup';
import { createGroupChat } from '../../redux/Chat/Action';

const NewGroup = ({ groupMember, setGroup }) => {
    const [isImageUploading, setIsImageUploading] = useState(false);
    const [groupImage, setGroupImage] = useState(null);
    const [groupName, setGroupName] = useState();
    const dispatch = useDispatch();
    const token = localStorage.getItem("token");
    const handleCreateGroup = () => {
        let userIds = [];
        for (let user of groupMember) {
            userIds.push(user.id);
        }
        const group = {
            userIds,
            chatName: groupName,
            chatImage: groupImage
        }
        const data = {
            group,
            token
        };
        console.log(data)
        dispatch(createGroupChat(data))
        setGroup(false);
    }

    const uploadToCloudinary = (pics) => {
        setIsImageUploading(true)
        const data = new FormData();
        data.append("file", pics);
        data.append("upload_preset", process.env.REACT_APP_UPLOAD_PRESET);
        data.append("cloud_name", process.env.REACT_APP_CLOUD_NAME);

        fetch(`https://api.cloudinary.com/v1_1/${process.env.REACT_APP_CLOUD_NAME}/image/upload`, {
            method: "post",
            body: data,
        })
            .then((res) => res.json())
            .then((data) => {
                const imageUrl = data.url.toString();
                setGroupImage(imageUrl);
                setIsImageUploading(false);
            })
            .catch((error) => {
                console.error("Error uploading image to Cloudinary:", error);
            });
    };

    return (
        <div className='w-full h-full'>
            <div className='flex items-center space-x-10 bg-[#878888] text-white pt-16 px-10 pb-5'>
                <BsArrowLeft className='cursor-pointer text-2xl font-bold' />
                <p className='text-xl font-semibold'>New Group</p>
            </div>
            <div className='flex flex-col justify-center items-center my-12'>
                <label htmlFor="imgInput" className='relative'>
                    <img className='rounded-full w-[15vw] h-[15vw] cursor-pointer' src={groupImage || "https://cdn.pixabay.com/photo/2024/02/17/15/14/crows-8579540_960_720.jpg"} alt="" />
                    {isImageUploading && <CircularProgress className='absolute top-[5rem] left-[6rem]' />}
                </label>
                <input type="file" id='imgInput' className='hidden' onChange={(e) => uploadToCloudinary(e.target.files[0])} />
            </div>
            <div className='w-full flex justify-between items-center py-2 px-5'>
                <input className='w-full outline-none border-b-2 border-gray-700 px-2 bg-transparent' placeholder="Group Subject" value={groupName} type="text" onChange={(e) => setGroupName(e.target.value)} />

            </div>

            {groupName && <div className='py-10 flex items-center justify-center' >
                <Button onClick={handleCreateGroup}>
                    <div className='bg-[#706e6e] rounded-full p-4 '>
                        <BsCheck2 className='text-white font-bold text-3xl' />
                    </div>
                </Button>
            </div>
            }

        </div>
    )
}

export default NewGroup