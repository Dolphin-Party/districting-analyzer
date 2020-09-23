import React, {useState} from 'react'
import {Link} from 'react-router-dom'
import NameLogo from "../assets/media/logos/Dolphin-Party.png"

const navLinks = [
  { title: 'HOME',
    path: '/',
  },
  {
    title: 'ABOUT',
    path: '/about'
  },
  {
    title: 'MEET THE TEAM',
    path: '/meet-the-team'
  },
  {
    title: 'REFERENCES',
    path: '/references'
  }
]

export default function Navigation({user}) {
  const [menuActive, setMenuActive] = useState(false)
    return (
    <nav className="site-navigation" role='navigation'>
      <div className="leftside-logos">
        <img className="menu-logo" src={NameLogo} />
        <span className="menu-title">Districting Analyzer</span>
      </div>
      <div className={`menu-content-container ${menuActive && 'active'}`}>
        <ul>
          { navLinks.map((link, index) => (
            <li key={index}>
              <Link to={link.path}>{link.title}</Link>
            </li>
          ))
        }
        </ul>
      </div>
      <i className="ionicons icon ion-ios-menu" onClick={() => setMenuActive(!menuActive)}/>
    </nav>)
}
