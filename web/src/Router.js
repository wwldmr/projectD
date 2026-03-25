import React from 'react';
import { useSelector } from 'react-redux';
import AppLayout from './components/layout/AppLayout';
import PlaceholderPage from './components/pages/PlaceholderPage';
import SimTasksPage from './components/pages/SimTasksPage';
import SimDescriptionPage from './components/pages/SimDescriptionPage';
import LoginPage from './components/LoginPage';

export default function Router() {
    const path = useSelector(s => s.routing.pathname);
    const selectedSimCardId = useSelector(s => s.ui.selectedSimCardId);
    const isAuthenticated = useSelector(s => s.auth.isAuthenticated);

    if (!isAuthenticated) {
        return <LoginPage />;
    }

    let content;

    const descriptionMatch = path.match(/^\/sim-cards\/(\d+)\/description$/);

    if (path === '/login' || path === '/' || path === '/workspace') {
        content = <PlaceholderPage titleText="Рабочий стол" />;
    } else if (path === '/administration') {
        content = <PlaceholderPage titleText="Администрирование" />;
    } else if (path === '/analytics') {
        content = <PlaceholderPage titleText="Аналитический блок" />;
    } else if (path === '/sim-cards') {
        content = <SimTasksPage />;
    } else if (path === '/sim-cards/description') {
        content = <SimDescriptionPage simId={selectedSimCardId} />;
    } else if (descriptionMatch) {
        content = <SimDescriptionPage simId={Number(descriptionMatch[1])} />;
    } else if (path === '/reports') {
        content = <PlaceholderPage titleText="Отчеты" />;
    } else if (path === '/requests') {
        content = <PlaceholderPage titleText="Заявки" />;
    } else {
        content = <PlaceholderPage titleText="Страница не найдена" />;
    }

    return <AppLayout>{content}</AppLayout>;
}
