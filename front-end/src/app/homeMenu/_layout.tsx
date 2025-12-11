import CustomDrawerContent from "@/components/home&others/CustomDrawer";
import { Drawer } from "expo-router/drawer";
import React from "react";

export default function RootLayout() {
  return (
    <Drawer
      drawerContent={(props) => <CustomDrawerContent {...props}/>}
      
      screenOptions={{
        headerShown: false, // O header customizado virá do layout das Tabs
        drawerStyle: { width: '70%' }
      }}
    >
      <Drawer.Screen
        name="(tabs)"
        options={{ drawerLabel: "Menu Principal" }}
      />
      <Drawer.Screen
        name="profileSettings"
        options={{ drawerLabel: "Configurações de Perfil" }}
      />
      <Drawer.Screen
        name="notificationSettings"
        options={{ drawerLabel: "Configurações de Notificações" }}
      />
      <Drawer.Screen
        name="birthdays"
        options={{ drawerLabel: "Aniversáriantes" }}
      />
      <Drawer.Screen
        name="management"
        options={{ drawerLabel: "Gerenciamento" }}
      />
    </Drawer>
  );
}