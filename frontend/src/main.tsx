import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { App } from './App';
import './index.css';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);

// Registro automático do Service Worker para o suporte a PWA em Produção [^1]
if ('serviceWorker' in navigator && import.meta.env.PROD) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/sw.js')
      .then(reg => console.log('PWA Service Worker registrado com sucesso: ', reg.scope))
      .catch(err => console.log('Falha ao registrar o Service Worker do PWA: ', err));
  });
}
