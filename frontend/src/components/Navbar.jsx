import React from 'react';
import { Link } from 'react-router-dom';
import Logo from './Logo';

export default function Navbar() {
    return (
        <header className='flex justify-between border-b border-gray-300 p-2'>
            <Link to='/'>
                <Logo image='LOL_Icon.png' text='롤문철 닷컴'></Logo>
            </Link>
            <nav className='flex items-center gap-6 text-2xl'>
                <Link to='/ranking' >랭킹</Link>
                <Link to='/elections'>재판</Link>
                <Link to='/login'>
                    <button>로그인</button>
                </Link>
            </nav>
        </header>
    );
}

