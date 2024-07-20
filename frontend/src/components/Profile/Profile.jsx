import React, { useState, useEffect } from 'react';
import { BsArrowLeft, BsCheck2, BsPencil } from 'react-icons/bs';
import { useNavigate } from 'react-router-dom';
import { updateUser } from '../../redux/Auth/Action';
import { useDispatch, useSelector } from 'react-redux';

const Profile = ({ handleCloseOpenProfile }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [username, setUsername] = useState('');
    const [tempPicture, setTempPicture] = useState(null);

    const { auth } = useSelector(store => store);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        if (auth.reqUser?.username) {
            setUsername(auth.reqUser.username);
        }
    }, [auth.reqUser]);

    const handleCheckClick = () => {
        setIsEditing(false);
        const data = {
            id: auth.reqUser?.id,
            token: localStorage.getItem("token"),
            data: { username }
        };
        dispatch(updateUser(data));
    };

    const handleChange = (e) => {
        setUsername(e.target.value);
    };

    const handleUpdateName = (e) => {
        if (e.key === "Enter") {
            handleCheckClick();
        }
    };

    const uploadToCloudinary = (pics) => {
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
                setTempPicture(imageUrl);

                const updateData = {
                    id: auth.reqUser.id,
                    token: localStorage.getItem("token"),
                    data: { profile_image: imageUrl },
                };
                dispatch(updateUser(updateData));
            })
            .catch((error) => {
                console.error("Error uploading image to Cloudinary:", error);
            });
    };

    return (
        <div className='w-full h-full'>
            <div className='flex items-center space-x-10 bg-gray-300 text-white pt-16 px-10 pb-5'>
                <BsArrowLeft className='cursor-pointer text-2xl font-bold' onClick={handleCloseOpenProfile} />
                <p className='cursor-pointer font-semibold'>Profile</p>
            </div>

            {/** Update profile picture section */}
            <div className='flex flex-col justify-center items-center my-12'>
                <label htmlFor="imgInput">
                    <img
                        className='rounded-full w-[15vw] h-[15vw] cursor-pointer'
                        src={auth.reqUser?.profile_image || tempPicture || "https://cdn.pixabay.com/photo/2023/05/27/22/56/kitten-8022452_960_720.jpg"}
                        alt="Profile"
                    />
                </label>
                <input
                    onChange={(e) => uploadToCloudinary(e.target.files[0])}
                    type="file"
                    id='imgInput'
                    className='hidden'
                />
            </div>

            {/** Name section */}
            <div className='bg-white px-3'>
                <p className='py-3'>Username</p>
                {!isEditing ? (
                    <div className='w-full flex justify-between items-center'>
                        <p className='py-3'>{auth.reqUser.username || "username"}</p>
                        <BsPencil onClick={() => setIsEditing(true)} className='cursor-pointer' />
                    </div>
                ) : (
                    <div className='w-full flex justify-between items-center py-2'>
                        <input
                            onKeyPress={handleUpdateName}
                            onChange={handleChange}
                            value={username}
                            className='w-[80%] outline-none border-b-2 border-gray-700 p-2'
                            type="text"
                            placeholder='Enter your name'
                        />
                        <BsCheck2 onClick={handleCheckClick} className='cursor-pointer text-2xl' />
                    </div>
                )}
            </div>

            <div className='px-3 my-5'>
                <p className='py-10'>
                    This is not your username; this name will be visible to your WhispareSafe contacts.
                </p>
            </div>
        </div>
    );
};

export default Profile;