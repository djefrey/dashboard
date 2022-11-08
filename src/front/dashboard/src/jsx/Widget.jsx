import { AiOutlineDelete } from 'react-icons/ai';
import { useState } from 'react';

export default function Widget({ name, nbr, functionDelete}) {
  const [size, setSize] = useState(2);
  const handleSize = (event) => {
    if (size === 2)
      setSize(1);
    else
      setSize(2);
  }
  // widget w-11/12 h-2/6 bg-navbar rounded-[30px] mt-20 ml-5 mr-5 flex justify-center

  return (
    <div className={
      size === 2 ? 'w-11/12 h-2/6 bg-navbar rounded-[30px] mt-20 ml-5 mr-5 flex justify-center'
      : 'w-5/12 h-2/6 bg-navbar rounded-[30px] mt-20 ml-8 mr-8 flex justify-center'
    }>
      <div className="w-11/12 mt-4 flex-y text-xl">
        <div className="w-full flex justify-between">
          <p className="text-white">{name}</p>
          <button onClick={() => functionDelete(nbr)} className="h-min bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full">
            <AiOutlineDelete className="fill-white" />
          </button>
        </div>
        <div className="w-full m-auto justify-center flex">
          <button onClick={() => handleSize()} className="text-white border-2">
            click to resize
          </button>
        </div>
      </div>
    </div>
  );
}