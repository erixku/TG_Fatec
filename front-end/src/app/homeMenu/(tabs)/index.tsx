import React, { useState, useEffect } from "react";
import { View } from "react-native";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import ScheduleList from "@/components/home&others/start/SchedulesList";
import { useMinistryRole } from "@/hooks/useMinistryRole";
import WarningFormModal from "@/components/home&others/WarningFormModal";
import ScheduleFormModal from "@/components/home&others/ScheduleFormModal";
import CommitmentFormModal from "@/components/home&others/CommitmentFormModal";
import { 
    getMinistries, 
    getWarningsByMinistry, 
    getSchedulesByMinistry, 
    getCommitmentsByMinistry,
    addWarning,
    addSchedule,
    addCommitment,
    generateId,
    getUser
} from "@/services/localCache";
import dayjs from "dayjs";

export default function HomePage() {
    const [ministryId, setMinistryId] = useState<string | null>(null);
    const { isLeader, isLoading, role } = useMinistryRole(ministryId);

    const [showWarningModal, setShowWarningModal] = useState(false);
    const [showScheduleModal, setShowScheduleModal] = useState(false);
    const [showCommitmentModal, setShowCommitmentModal] = useState(false);

    const [warnings, setWarnings] = useState<any[]>([]);
    const [schedules, setSchedules] = useState<any[]>([]);
    const [commitments, setCommitments] = useState<any[]>([]);

    // Carrega o ID do ministério ao montar o componente
    useEffect(() => {
        loadMinistryId();
    }, []);

    const loadMinistryId = async () => {
        try {
            const ministries = await getMinistries();
            if (ministries.length > 0) {
                const ministry = ministries[0];
                setMinistryId(ministry.id);
                console.log('🙏 Ministério carregado:', ministry.nome, '- ID:', ministry.id);
                
                // Carrega dados do ministério
                await loadMinistryData(ministry.id);
            }
        } catch (error) {
            console.error('❌ Erro ao carregar ministério:', error);
        }
    };

    const loadMinistryData = async (ministryId: string) => {
        try {
            const [warningsData, schedulesData, commitmentsData] = await Promise.all([
                getWarningsByMinistry(ministryId),
                getSchedulesByMinistry(ministryId),
                getCommitmentsByMinistry(ministryId)
            ]);

            // Mapeia para formato compatível com os componentes
            setWarnings(warningsData.map(w => ({
                _id: w.id,
                key: w.id,
                title: w.title,
                description: w.description,
                date: dayjs(w.date).format('DD/MM/YYYY'),
            })));

            setSchedules(schedulesData.map(s => ({
                _id: s.id,
                key: s.id,
                title: s.title,
                description: s.description,
                date: dayjs(s.date).format('DD/MM/YYYY'),
                time: s.time,
                type: 'schedule'
            })));

            setCommitments(commitmentsData.map(c => ({
                _id: c.id,
                key: c.id,
                title: c.title,
                description: c.description,
                date: dayjs(c.date).format('DD/MM/YYYY'),
                time: c.time,
                responsible_user: c.responsible_user,
                type: 'commitment'
            })));

            console.log('✅ Dados carregados:', {
                avisos: warningsData.length,
                agendamentos: schedulesData.length,
                compromissos: commitmentsData.length,
            });
        } catch (error) {
            console.error('❌ Erro ao carregar dados do ministério:', error);
        }
    };

    const handleCreateWarning = async (data: any) => {
        try {
            if (!ministryId) return;
            
            const user = await getUser();
            if (!user) throw new Error('Usuário não encontrado');

            await addWarning({
                id: generateId(),
                idMinisterio: ministryId,
                title: data.title,
                description: data.description,
                date: new Date().toISOString(),
                createdBy: user.id,
                createdAt: new Date().toISOString(),
            });

            // Recarrega dados
            await loadMinistryData(ministryId);
            console.log('✅ Aviso criado com sucesso');
        } catch (error) {
            console.error('❌ Erro ao criar aviso:', error);
        }
    };

    const handleCreateSchedule = async (data: any) => {
        try {
            if (!ministryId) return;
            
            const user = await getUser();
            if (!user) throw new Error('Usuário não encontrado');

            await addSchedule({
                id: generateId(),
                idMinisterio: ministryId,
                title: data.title,
                description: data.description,
                date: data.date.toISOString(),
                time: data.time,
                createdBy: user.id,
                createdAt: new Date().toISOString(),
            });

            // Recarrega dados
            await loadMinistryData(ministryId);
            console.log('✅ Agendamento criado com sucesso');
        } catch (error) {
            console.error('❌ Erro ao criar agendamento:', error);
        }
    };

    const handleCreateCommitment = async (data: any) => {
        try {
            if (!ministryId) return;
            
            const user = await getUser();
            if (!user) throw new Error('Usuário não encontrado');

            await addCommitment({
                id: generateId(),
                idMinisterio: ministryId,
                title: data.title,
                description: data.description,
                date: data.date.toISOString(),
                time: data.time,
                responsible_user: data.responsible_user,
                createdBy: user.id,
                createdAt: new Date().toISOString(),
            });

            // Recarrega dados
            await loadMinistryData(ministryId);
            console.log('✅ Compromisso criado com sucesso');
        } catch (error) {
            console.error('❌ Erro ao criar compromisso:', error);
        }
    };
    
    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex py-10 h-full w-[90%] lg:w-[50%] items-center justify-center gap-y-4 rounded-xl">
                        <ScheduleList 
                            warning 
                            label="Avisos" 
                            schedules={warnings}
                            showAddButton={isLeader}
                            onAddPress={() => setShowWarningModal(true)}
                        />
                        <ScheduleList 
                            label="Agendamentos" 
                            schedules={schedules}
                            showAddButton={isLeader}
                            onAddPress={() => setShowScheduleModal(true)}
                        />
                        <ScheduleList 
                            commitmend 
                            label="Compromissos" 
                            schedules={commitments}
                            showAddButton={isLeader}
                            onAddPress={() => setShowCommitmentModal(true)}
                        />
                    </View>
                </View>
            </View>

            {/* Modais de Formulário */}
            <WarningFormModal
                visible={showWarningModal}
                onClose={() => setShowWarningModal(false)}
                onSubmit={handleCreateWarning}
            />
            <ScheduleFormModal
                visible={showScheduleModal}
                onClose={() => setShowScheduleModal(false)}
                onSubmit={handleCreateSchedule}
            />
            <CommitmentFormModal
                visible={showCommitmentModal}
                onClose={() => setShowCommitmentModal(false)}
                onSubmit={handleCreateCommitment}
            />
        </AnimatedScreenWrapper>
    );
}