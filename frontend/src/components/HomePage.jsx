import React, { useEffect, useRef, useState } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
import { BiCommentDetail } from 'react-icons/bi';
import { TbCircleDashed } from 'react-icons/tb';
import { BsEmojiSmile, BsFilter, BsMicFill, BsThreeDotsVertical } from 'react-icons/bs';
import ChatCard from './ChatCard/ChatCard';
import MessageCard from './MessageCard/MessageCard';
import { ImAttachment } from 'react-icons/im';
import "./HomePage.css";
import { useNavigate } from 'react-router-dom';
import Profile from './Profile/Profile';
import { Button, Menu, MenuItem } from '@mui/material';
import CreateGroup from './Group/CreateGroup';
import { useDispatch, useSelector } from 'react-redux';
import { currentUser, logoutAction, searchUser } from '../redux/Auth/Action';
import { createChat, getUsersChat } from '../redux/Chat/Action';
import { createMessage, getAllMessages } from '../redux/Message/Action';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';

const HomePage = () => {
  const [stompClient, setStompClient] = useState(null);
  const [isConnect, setIsConnect] = useState(false);
  const [messages, setMessages] = useState([]);
  const [querys, setQuerys] = useState('');
  const [currentChat, setCurrentChat] = useState(null);
  const [content, setContent] = useState('');
  const [group, setGroup] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const [isProfile, setIsProfile] = useState(false);
  const open = Boolean(anchorEl);

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { auth, chat, message } = useSelector(store => store);
  const token = localStorage.getItem("token");

  const getCookie = (name) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
      return parts.pop().split(";").shift();
    }
    return null; // Çerez bulunamazsa null döndürün
  };

  const connect = () => {
    const sock = new SockJS("http://localhost:4747/ws");
    const temp = over(sock);
    setStompClient(temp);
    const headers = {
      Authorization: `Bearer ${token}`

    };

    temp.connect(headers,
      () => {
        setIsConnect(true);
        console.log("Connected to server");
      },
      (error) => {
        setIsConnect(false);
        console.error("Connection error:", error);
      }
    );
  };

  const onError = (error) => {
    console.log("Connection error", error);
  };

  const onMessageReceive = (payload) => {
    console.log("Received message", JSON.parse(payload.body));
    const receivedMessage = JSON.parse(payload.body);
    setMessages(prevMessages => [...prevMessages, receivedMessage]);
  };

  useEffect(() => {
    if (message.newMessage && stompClient && isConnect) {
      setMessages(prevMessages => [...prevMessages, message.newMessage]);
      stompClient.send("/app/message", {}, JSON.stringify(message.newMessage));
    }
  }, [message.newMessage, stompClient, isConnect]);

  useEffect(() => {
    if (isConnect && stompClient && auth.reqUser && currentChat) {
      const subscription = stompClient.subscribe(`/topic/${currentChat.id}`, onMessageReceive);
      return () => {
        subscription.unsubscribe();
      };
    }
  }, [isConnect, stompClient, auth.reqUser, currentChat]);

  useEffect(() => {
    connect();
  }, []);

  useEffect(() => {
    setMessages(message.messages);
  }, [message.messages]);

  const handleSearch = (keyword) => {
    dispatch(searchUser({ keyword, token }));
  };

  const handleCreateChat = (userId) => {
    dispatch(createChat({ token, data: { userId } }));
  };

  const handleCreateNewMessage = () => {
    if (isConnect && stompClient) {
      dispatch(createMessage({ token, data: { chatId: currentChat.id, content } }));
      console.log("Created new message");
      setContent("");
    } else {
      console.log("Connection not established yet");
    }
  };

  const handleClick = (e) => {
    setAnchorEl(e.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleClickOnChatCard = (userId) => {
    dispatch(createChat({ token, data: { userId } }));
  };

  const handleNavigate = () => {
    setIsProfile(true);
  };

  const handleCloseOpenProfile = () => {
    setIsProfile(false);
  };

  const handleCreateGroup = () => {
    setGroup(true);
  };

  useEffect(() => {
    dispatch(currentUser(token));
  }, [token, dispatch]);

  const handleLogout = () => {
    dispatch(logoutAction());
    navigate("/signup");
  };

  useEffect(() => {
    if (!auth.reqUser) {
      navigate("/signup");
    }
  }, [auth.reqUser, navigate]);

  useEffect(() => {
    dispatch(getUsersChat({ token }));
  }, [chat.createdChat, chat.createdGroup, dispatch, token]);

  const handleCurrentChat = (item) => {
    setCurrentChat(item);
  };

  useEffect(() => {
    if (currentChat?.id) {
      dispatch(getAllMessages({ chatId: currentChat.id, token }));
    }
  }, [currentChat, message.newMessage, dispatch, token]);

  const messagesEndRef = useRef(null);
  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]);


  return (
    <div className='relative'>
      <div className='w-full py-14 bg-[#060a09]'></div>
      <div className='flex bg-[#f0f2f5] h-[90vh] absolute top-[5vh] left-[2vw] w-[96vw]'>
        <div className='left w-[30%] bg-[#e8e9ec] h-full '>
          {group && <CreateGroup setGroup={setGroup} />}
          {isProfile && <div className='w-full h-full'><Profile handleCloseOpenProfile={handleCloseOpenProfile} /></div>}
          {!isProfile && !group && (
            <div className='w-full'>
              <div className='flex justify-between items-center p-3'>
                <div onClick={handleNavigate} className='flex items-center space-x-3'>
                  <img className='rounded-full w-10 h-10 cursor-pointer'
                    src={auth.reqUser?.profile_image || "https://cdn.pixabay.com/photo/2017/02/25/22/04/user-icon-2098873_960_720.png"} alt="" />
                  <p>{auth.reqUser?.username}</p>
                </div>
                <div className='space-x-3 text-2xl flex'>
                  <TbCircleDashed className='cursor-pointer' onClick={() => navigate("/status")} />
                  <BiCommentDetail />
                  <div>
                    <BsThreeDotsVertical id="basic-button" aria-controls={open ? 'basic-menu' : undefined} aria-haspopup="true" aria-expanded={open ? 'true' : undefined} onClick={handleClick} />
                    <Menu id="basic-menu" anchorEl={anchorEl} open={open} onClose={handleClose} MenuListProps={{ 'aria-labelledby': 'basic-button', }}>
                      <MenuItem onClick={handleClose}>Profile</MenuItem>
                      <MenuItem onClick={handleCreateGroup}>Create Group</MenuItem>
                      <MenuItem onClick={handleLogout}>Logout</MenuItem>
                    </Menu>
                  </div>
                </div>
              </div>
              <div className='relative flex justify-center items-center bg-white py-4 px-3'>
                <input className='border-none outline-none bg-slate-200 rounded-md w-[93%] pl-9 py-2' type='text' placeholder='Search or start new Chat'
                  onChange={(e) => { setQuerys(e.target.value); handleSearch(e.target.value); }} value={querys} />
                <AiOutlineSearch className='left-5 top-7 absolute ' />
                <div><BsFilter className='ml-4 text-3xl' /></div>
              </div>
              <div className='bg-white overflow-y-scroll h-[72vh] px-2'>
                {querys && auth.searchUser?.map((item) => (
                  <div onClick={() => handleClickOnChatCard(item.id)} key={item.id}>
                    <hr />
                    <ChatCard name={item.username} userImage={item.profile_image || "https://cdn.pixabay.com/photo/2017/02/25/22/04/user-icon-2098873_960_720.png"} />
                  </div>
                ))}
                {chat.chats.length > 0 && !querys && chat.chats.map((item) => (
                  <div onClick={() => handleCurrentChat(item)} key={item.id}>
                    <hr />
                    {!item.group && (
                      <ChatCard name={auth.reqUser.id !== item.users[0].id ? item.users[0].username : item.users[1].username}
                        userImage={auth.reqUser.id !== item.users[0].id ? item.users[0].profile_image ||
                          "https://cdn.pixabay.com/photo/2017/02/25/22/04/user-icon-2098873_960_720.png" :
                          item.users[1].profile_image ||
                          "https://cdn.pixabay.com/photo/2017/02/25/22/04/user-icon-2098873_960_720.png"} />
                    )}
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>

        {!currentChat && (
          <div className='w-[70%] flex flex-col items-center justify-center h-full'>
            <div className='max-w-[70%] text-center '>
              <img src="https://cdn.pixabay.com/photo/2016/03/31/19/14/chat-1294839_960_720.png" alt="" />
              <h1 className='text-4xl text-gray-600'>WhispareSafe</h1>
              <p className='my-9'>Welcome to WhispareSafe! Stay connected, send and receive your messages from anywhere..</p>
            </div>
          </div>
        )}

        {currentChat && (
          <div className='w-[70%] relative bg-gray-200'>
            <div className='header absolute top-0 w-full bg-[#f0f2f5]'>
              <div className='flex justify-between'>
                <div className='py-3 space-x-4 flex items-center px-3'>
                  <img className='w-10 h-10 rounded-full' src={currentChat.group ? currentChat.chat_image || "https://cdn.pixabay.com/photo/2016/04/15/18/05/computer-1331579_960_720.png" : (auth.reqUser.id !== currentChat.users[0].id ? currentChat.users[0].profile_image || "https://cdn.pixabay.com/photo/2017/02/25/22/04/user-icon-2098873_960_720.png" : currentChat.users[1].profile_image || "https://cdn.pixabay.com/photo/2017/02/25/22/04/user-icon-2098873_960_720.png")} alt="" />
                  <p>{currentChat.group ? currentChat.chat_name : (auth.reqUser?.id === currentChat.users[0].id ? currentChat.users[1].username : currentChat.users[0].username)}</p>
                </div>
                <div className='py-3 flex space-x-4 items-center px-3'>
                  <BsThreeDotsVertical id="basic-button" aria-controls={open ? 'basic-menu' : undefined} aria-haspopup="true" aria-expanded={open ? 'true' : undefined} onClick={handleClick} />
                  {/*}  <Menu id="basic-menu" anchorEl={anchorEl} open={open} onClose={handleClose} MenuListProps={{ 'aria-labelledby': 'basic-button', }}>
                      <MenuItem onClick={handleClose}>Delete</MenuItem>
                      <MenuItem onClick={handleCreateGroup}>Create Group</MenuItem>
                      <MenuItem onClick={handleLogout}>Logout</MenuItem>
                    </Menu> */}
                </div>
              </div>
            </div>
            <div className='h-[85vh] overflow-y-scroll pt-20'> {/* Header ve Footer için padding ekledik */}
              <div className='space-y-1 flex flex-col justify-center border py-2'>
                {messages.length > 0 && messages.map((item) => (
                  <MessageCard key={item.id} isReqUserMessage={item.users && item.users.id === auth.reqUser.id} content={item.content} />
                ))}
                <div ref={messagesEndRef} /> {/* Mesajların sonunu takip eden referans */}
              </div>
            </div>
            <div className='footer bg-[#f0f2f5] absolute bottom-0 w-full py-3 text-2xl'>
              <div className='flex justify-between items-center px-5 relative'>
                <BsEmojiSmile className='cursor-pointer' />
                <ImAttachment />
                <input className='py-2 outline-none border-none bg-white pl-4 rounded w-[85%]' placeholder='Type message' value={content}
                  onKeyPress={(e) => {
                    if (e.key === "Enter") {
                      handleCreateNewMessage();
                    }
                  }}
                  type="text"
                  onChange={(e) => setContent(e.target.value)} />
                <BsMicFill />
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
