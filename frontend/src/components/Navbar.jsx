import React from 'react';
import { Link } from 'react-router-dom';

export default function Navbar() {
    return (
        <header className='flex justify-between border-b border-gray-300 p-2'>
            <Link to='/'><img src='logo.png' alt='logo' className='flex items-center'/></Link>
            <nav className='flex items-center gap-6 text-2xl'>
                <Link to='/ranking' >랭킹</Link>
                <Link to='/elections'>재판</Link>
                <button>로그인</button>
            </nav>
        </header>
    );
}

