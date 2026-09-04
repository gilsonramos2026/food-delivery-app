export const config = {
  apiUrl: (import.meta.env.VITE_API_URL as string) || 'http://localhost:8081',
  wsUrl: (import.meta.env.VITE_WS_URL as string) || 'ws://localhost:8081/ws-delivery',
  stripePublicKey: (import.meta.env.VITE_STRIPE_PUBLIC_KEY as string) || 'pk_test_sua_chave_publica_aqui'
};
