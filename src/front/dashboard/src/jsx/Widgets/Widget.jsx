import { AiOutlineDelete } from 'react-icons/ai';
import { useState } from 'react';
import { AiOutlineArrowLeft } from 'react-icons/ai'
import { AiOutlineArrowRight } from 'react-icons/ai'

export default function Widget({ id, functionDelete, functionUp, functionDown, widgetContent, noResize }) {
  const [size, setSize] = useState(2);
  const handleSize = (event) => {
    if (size === 2)
      setSize(1);
    else
      setSize(2);
  }

  return (
    <div className={
      size === 2
        ? 'h-100 bg-navbar rounded-[30px] mt-5 mr-1 ml-2 flex justify-center'
        : 'w-5/12 h-100 bg-navbar rounded-[30px] mt-5 ml-10 mr-10 flex justify-center'
    }>
      <div className="w-1/12">
        <button onClick={() => functionUp(id)}>
          <AiOutlineArrowLeft className="text-white mt-40 ml-2" />
        </button>
      </div>
      <div className="w-10/12 flex-y text-xl">
        <div className="flex justify-center text-center">
          <div className="w-full bg-navbar rounded-[30px] text-white ml-6 mr-6 pb-4 pt-2">
            {widgetContent}
          </div>
        </div>
        <div className="w-full mb-6 justify-around flex">
          <button disabled={noResize} onClick={() => handleSize()} className="text-white border-2">
            click to resize
          </button>
          <button onClick={() => functionDelete(id)} className="h-min bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full">
            <AiOutlineDelete className="fill-white" />
          </button>
        </div>
      </div>
      <div className="w-1/12">
        <button onClick={() => functionDown(id)}>
          <AiOutlineArrowRight className="text-white mt-40 -ml-2" />
        </button>
      </div>
    </div>
  );
}