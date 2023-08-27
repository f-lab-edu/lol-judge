import React from 'react';

export default function Logo({text, image}) {
    return (
        <div className='flex items-center'>
            <img src={image} alt='logo' className='object-scale-down h-16'></img>
            <span className='text-4xl font-bold'>{text}</span>
        </div>
    );
}

