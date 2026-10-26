import type { Metadata } from 'next';
import './globals.css';
export const metadata: Metadata = { title: 'УспешныйЕГЭ — аналитика', description: 'Статистика сайта из Tilda' };
export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) { return <html lang="ru"><body>{children}</body></html>; }
