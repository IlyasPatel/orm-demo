
def date = LocalDateTime.now()
def uuid = UUID.randomUUID()

def proposalNumber1 = 'RAA' + String.valueOf(Utils.fixedLengthNumber)


def randomNumber = String.valueOf(Utils.fixedLengthNumber)
def proposalTitle1 = 'The Test Automation Story Part 1 ' + randomNumber

def proposalStatus = Statuses.NETWORK_RECEIVED
def organisationOwning = Organisation.RADIO_DRAMA_LONDON
def numberOfEpisodes = 1


def CRB_Find = find(CommissioningRoundBriefEntity, '20252026-47549')


// Proposal
table(ProposalEntity, ['propProposalNumber', 'staStatusId', 'orgOwningOrgId', 'crOrganisationId', 'crRound', 'crYear', 'cbyNumber', 'propUniqId', 'proposalTitle', 'propProposedEpisodes', 'propProposedDuration', 'propCommissionedEpisodes', 'propCommissionedDuration', 'propProposedDeliveryDate', 'propLastMod', 'propLastModUser', 'propCreatorUser']) {
    proposal = row  proposalNumber1, proposalStatus, organisationOwning, CRB_Find.getOrgOrganisationId(), commissioningRound, CRB_Find.getCrYear(), CRB_Find.getCbyNumber(), uuid        , proposalTitle1, numberOfEpisodes, 3600, numberOfEpisodes, 300, date, date, 'nichoc26', 'nichoc26'
}






















/*
    Commissioning Brief

    table(CommissioningBrief, ['CB_NUMBER', 'SG_SUB_GENRE_ID', 'ORG_ORGANISATION_ID', 'CB_CREATOR_ORG_ID', 'CB_TITLE', 'CB_STATE', 'CB_SLOT_TIME', 'CB_SLOT_DAY_CODE', 'CB_DESCRIPTION', 'CB_LAST_MOD_USER', 'CB_NOT_FOR_COMMISSIONING']) {
        commissioningBrief = row   100279, 11100, 100, 100, 'Radio 1 In-house Programmes', 'A', 0, 'X', 'In-house production', 'STEELT01', true
    }

    table(CommissioningBriefYear, ['CBY_NUMBER', 'CB_NUMBER', 'CBY_SG_SUB_GENRE_ID', 'CBY_YEAR', 'CBY_EPISODES_EXPECTED', 'CBY_DURATION', 'CBY_ORIGINATION_LOW_PRICE_PENCE', 'CBY_ORIGINATION_HIGH_PRICE_PENCE', 'CBY_ORIGINATION_STANDARD_PRICE_PENCE', 'CBY_ORIGINATION_TXS_PURCHASED', 'CBY_REPEAT_LOW_PRICE_PENCE', 'CBY_REPEAT_HIGH_PRICE_PENCE', 'CBY_REPEAT_STANDARD_PRICE_PENCE', 'CBY_REPEAT_TXS_PURCHASED', 'CBY_PART_OF_SOPP', 'CBY_LAST_MOD_USER']) {
        commissioningBriefYear = row '19001901-100279', 100279, 10522, 19001901, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'DBA'
    }

    table(CommissioningRound, ['ORG_ORGANISATION_ID', 'CR_ROUND', 'CR_YEAR', 'CR_LAST_MOD_USER', 'CR_DESCRIPTION']) {
        commissioningRound = row 100, 1, 1, 'Autotesting', 'Commissioning Round Description'
    }
 */