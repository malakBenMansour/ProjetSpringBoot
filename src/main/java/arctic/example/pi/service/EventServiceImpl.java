package arctic.example.pi.service;
import arctic.example.pi.DTO.*;
import arctic.example.pi.entity.Sponsor;
import arctic.example.pi.repository.SponsorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import arctic.example.pi.entity.Evenement;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.EventRepository;
import arctic.example.pi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    EventRepository eventRepo;

    @Autowired
    SponsorRepository sponsorRepo;
    @Autowired
    UserRepository userRepo;

    @Autowired
    private JavaMailSender mailSender;

    public List<Evenement> retrieveAllEvent() {
        return (List<Evenement>) eventRepo.findAll();
    }

    @Override
    public List<Evenement> retrieveActiveEvents() {
        return (List<Evenement>) eventRepo.getActiveEvents();
    }


    public void addEvent(Evenement event) {
        /*
        List<Evenement> events = eventRepo.getEventCondition(event.getDateDebut(),event.getDateFin());
        if (events.isEmpty())
            eventRepo.save(event);*/
        eventRepo.save(event);
    }

    @Override
    public void updateEvenement(Evenement event) {
        Optional<Evenement> events = eventRepo.findById(event.getNumEvent());
        if (events.isPresent()) {
            events.get().setNomEvent(event.getNomEvent());
            events.get().setDescription(event.getDescription());
            events.get().setDateDebut(event.getDateDebut());
            events.get().setDateFin(event.getDateFin());
            events.get().setNbrPlace(event.getNbrPlace());
            events.get().setPrix(event.getPrix());
           /* events.get().setFileName(event.getFileName());
            events.get().setUsers(event.getUsers());
            events.get().setSponsors(event.getSponsors());*/
            eventRepo.save(event);

        }
    }

    public void removeEvenement(Long id) {
            eventRepo.deleteById(id);
    }

    public Optional<Evenement> retrieveEvent(Long numEvent) {
        return eventRepo.findById(numEvent);
    }

    @Override
    public void removeReservation(RemoveReservationRequest req) {

        Optional<Evenement> c = eventRepo.findById(req.getNumEvent());
        if (c.isPresent()) {
            for (Iterator<User> iterator = c.get().getUsers().iterator(); iterator.hasNext();) {
                User p = iterator.next();
                if (p.getId() == req.getId()) {
                    iterator.remove();
                }
            }
            eventRepo.save(c.get());
        }
    }


    @Override
    public void Reserver(ReservationRequest reservationRequest) throws IOException, WriterException, MessagingException {

        // Retrieve the user and event objects
        User u = userRepo.findById(reservationRequest
                .getId()).get();

        Evenement e = eventRepo.findById(reservationRequest.getNumEvent()).get();


        // Check if there are available places
        int availablePlaces = e.getNbrPlace() - e.getUsers().size();
        if (availablePlaces <= 0) {
            throw new RuntimeException("No available places for this event.");
        }else {

           // u.getEvent().add(e);
            e.getUsers().add(u);
            eventRepo.save(e);

            //creation QRCode
            Map<String, Object> qrcodeData = new HashMap<>();
            qrcodeData.put("User name", u.getPrenom());
            qrcodeData.put("Email address", u.getEmail());
            qrcodeData.put("Event name", e.getNomEvent());
            qrcodeData.put("Event price", e.getPrix());
            ObjectMapper objectMapper = new ObjectMapper();
            String qrcodeText = objectMapper.writeValueAsString(qrcodeData);
            byte[] qrCode = QRCodeGenerator.generateQRCodeImage(qrcodeText, 350, 350);

            String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCode);

            //Envoi du mail
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;

            helper = new MimeMessageHelper(message, true); // true indicates
            // multipart message
            helper.setTo(u.getEmail());
            helper.setSubject("Confirmation de la réservation");
            helper.setText("<div style=\"background-color:#F2F6E5;text-align:center\">\n" +
                    "   <div class=\"adM\">\n" +
                    "   </div>\n" +
                    "   <table style=\"width:560px;border:none;margin:0 auto 0 auto;text-align:left\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "      <tbody>\n" +
                    "         <tr>\n" +
                    "            <td style=\"height:25px;background-color:#F2F6E5;padding:0 0 0 3px;font-family:Arial,'Helvetica Neue',Helvetica,sans-serif;font-size:9px;color:#ffffff\">\n" +
                    "               <span class=\"il\">" +
                    "            </td>\n" +
                    "         </tr>\n" +
                    "         <tr>\n" +
                    "            <td style=\"background-color:#ffffff\">\n" +
                    "               <table cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                  <tbody>\n" +
                    "                     <tr>\n" +
                    "                        <td style=\"padding:22px 22px 12px 22px;text-align:center\" width=\"544\">\n" +
                    "                           <div style=\"background-color:#DCF18F\">\n" +
                    "                              <img id=\"m_3184415673555683133banner\" src=\"\" alt=\"Banner\" style=\"max-width:544px\" class=\"CToWUd\" data-bit=\"iit\">\n" +
                    "                           </div>\n" +
                    "                        </td>\n" +
                    "                     </tr>\n" +
                    "                     <tr>\n" +
                    "                        <td style=\"padding:0 22px 30px 22px;text-align:center\">\n" +
                    "                           <table cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                              <tbody>\n" +
                    "                                 <tr>\n" +
                    "                                    <td style=\"padding:23px 14px 23px 14px;background-color:#DCF18F ;width:516px\">\n" +
                    "                                       <table cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                                          <tbody>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"font-family:Tahoma,Verdana,Segoe,sans-serif;font-size:19px;line-height:29px;background-color:#DCF18F;color:#3b3b3b;padding:0 0 34px 0\">\n" +
                    "                                                   Bonjour " + u.getPrenom() + " " + u.getPrenom() + ", merci pour votre achat.\n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"font-family:Tahoma,Verdana,Segoe,sans-serif;font-size:14px;line-height:23px;background-color:#DCF18F;color:#3b3b3b\">\n" +
                    "                                                   Cet email est la confirmation de votre réservation.\n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"font-family:Tahoma,Verdana,Segoe,sans-serif;font-size:14px;line-height:23px;background-color:#DCF18F;color:#3b3b3b\">\n" +
                    "                                                   Bon événement!\n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +
                    "                                          </tbody>\n" +
                    "                                       </table>\n" +
                    "                                    </td>\n" +
                    "                                 </tr>\n" +
                    "                              </tbody>\n" +
                    "                           </table>\n" +
                    "                        </td>\n" +
                    "                     </tr>\n" +
                    "                     <tr>\n" +
                    "                        <td style=\"padding:0 36px 0 36px;text-align:center\">\n" +
                    "                           <table>\n" +
                    "                              <tbody>\n" +
                    "                                 <tr>\n" +
                    "                                    <td style=\"padding:25px 14px 14px 14px;border:solid 1px #000000\">\n" +
                    "                                       <table cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                                          <tbody>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"font-family:Tahoma,Verdana,Segoe,sans-serif;font-size:21px;font-weight:bold;line-height:23px;text-transform:uppercase;padding:0 7px 8px 0;color:#5ECECF\">" +
                    "                                                   Récapitulatif d'achat \n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"font-weight:bold;font-size:14px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:23px;background-color:#ffffff;color:#3b3b3b;padding:16px 7px 0 7px;width:458px\" colspan=\"3\">\n" +
                    "                                                   Nom de l'événement: " + e.getNomEvent() + "\n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"padding:0 7px 0 7px;background-color:#ffffff\">\n" +
                    "                                                   <table style=\"border:none;margin:0 0 0 0;background-color:#ffffff\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                                                      <tbody>\n" +
                    "                                                         <tr>\n" +
                    "                                                            <td style=\"font-size:14px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:23px;color:#3b3b3b;padding:0 7px 0 0;vertical-align:text-top\">\n" +
                    "                                                               Description :\n" +
                    "                                                            </td>\n" +
                    "                                                            <td style=\"font-size:14px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:23px;color:#3b3b3b;vertical-align:text-top\">\n" +
                    "                                                               <div>\n" + e.getDescription() +
                    "\n" +
                    "                                                               </div>\n" +
                    "                                                            </td>\n" +
                    "                                                         </tr>\n" +
                    "                                                      </tbody>\n" +
                    "                                                   </table>\n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"font-size:14px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:23px;background-color:#ffffff;color:#3b3b3b;padding:0 7px 0 7px;vertical-align:text-top\" colspan=\"3\">\n" +
                    "                                                   Date: " + e.getDateDebut() + "-" + e.getDateFin() + "\n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"border-bottom:solid 1px #000000\" colspan=\"3\"></td>\n" +
                    "                                             </tr>\n" +
                    "                                             <tr>\n" +
                    "                                                <td style=\"font-size:14px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:23px;background-color:#ededed;color:#3b3b3b;padding:0 0 0 7px;vertical-align:text-top\">\n" +
                    "                                                   Prix total:\n" +
                    "                                                </td>\n" +
                    "                                                <td style=\"font-size:14px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:23px;background-color:#ededed;color:#3b3b3b;padding:0 7px 0 0;text-align:right;vertical-align:text-top\" colspan=\"2\">\n" +
                    e.getPrix() + "\n" +
                    "                                                </td>\n" +
                    "                                             </tr>\n" +

                    "                                          </tbody>\n" +
                    "                                       </table>\n" +
                    "                                    </td>\n" +
                    "                                 </tr>\n" +
                    "                              </tbody>\n" +
                    "                           </table>\n" +
                    "                        </td>\n" +
                    "                     </tr>\n" +
                    "                     <tr>\n" +
                    "                        <td>\n" +
                    "                           <table style=\"width:100%\">\n" +
                    "                              <tbody>\n" +
                    "                                 <tr>\n" +
                    "                                    <td style=\"font-weight:bold;font-size:12px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:20px;color:#3b3b3b;padding:30px 36px 0 36px\">\n" +
                    "                                       Nous Contacter:  \n" +
                    "                                    </td>\n" +
                    "                                    <td rowspan=\"2\" style=\"vertical-align:top;padding:30px 36px 0 0;text-align:right\">\n" +
                    "                                       <img src='data:image/png;base64," + qrCodeBase64 + "'/>\n" +
                    "                                    </td>\n" +
                    "                                 </tr>\n" +
                    "                                 <tr>\n" +
                    "                                    <td style=\"font-size:12px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:20px;color:#3b3b3b;padding:0 36px 0 36px\">\n" +
                    "                                       <span class=\"il\">Foody</span> Food Waste Management <br>\n" +
                    "                                       Ecole supérieur ESPRIT  <br>\n" +
                    "                                       ElGhazela , Tunisie <br>\n" +
                    "                                       2032<br>\n" +
                    "                                    </td>\n" +
                    "                                 </tr>\n" +
                    "                              </tbody>\n" +
                    "                           </table>\n" +
                    "                        </td>\n" +
                    "                     </tr>\n" +
                    "                     <tr>\n" +
                    "                        <td style=\"font-size:12px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:20px;color:#3b3b3b;padding:30px 36px 30px 36px\">\n" +
                    "                           Date d'édition: " + LocalDate.now() + "\n" +
                    "                        </td>\n" +
                    "                     </tr>\n" +
                    "                     <tr>\n" +
                    "                        <td style=\"font-weight:bold;padding:0 36px 0 36px;font-size:12px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:13px;color:#3b3b3b\">\n" +
                    "                           Merci pour votre achat.\n" +
                    "                        </td>\n" +
                    "                     </tr>\n" +
                    "                     <tr>\n" +
                    "                        <td style=\"font-size:12px;font-family:Tahoma,Verdana,Segoe,sans-serif;line-height:13px;color:#3b3b3b;padding:0 36px 30px 36px\">\n" +
                    "                           Veuillez&nbsp;apporter une copie de ce mail a l'événement pour validation : soit l'imprimer, soit nous la montrer sur place depuis votre smartphone ! N'oubliez pas de vous rendre sur place en avance. Merci et bon événement !\n" +
                    "                        </td>\n" +
                    "                     </tr>\n" +
                    "                  </tbody>\n" +
                    "               </table>\n" +
                    "            </td>\n" +
                    "         </tr>\n" +
                    "      </tbody>\n" +
                    "   </table>\n" +
                    "</div>", true);


            helper.addAttachment("event_qr_code.png", new ByteArrayResource(qrCode), "image/png");

            mailSender.send(message);
        }
    }

    @Override
    public int numberPlacesAvailablePerEvent(Long id) {
         Evenement event = eventRepo.findById(id).get();
            return event.getNbrPlace() - event.getUsers().size();
      }

    @Override
    public List<Sponsor> getSponsorNonDuEvent(Long id) {
        Optional<Evenement> c = eventRepo.findById(id);
        List <Sponsor> listQ = (List<Sponsor>) sponsorRepo.findAll();
        List <Sponsor> listQF = c.get().getSponsors();
        listQ.removeAll(listQF);
        if (Boolean.FALSE.equals(listQ.isEmpty())) {
            return listQ;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Sponsor> getSponsorsDuEvent(Long id) {
        Optional<Evenement> c = eventRepo.findById(id);
        if (c.isPresent()) {
            return c.get().getSponsors();
        } else {
            return Collections.emptyList();
        }
    }
    @Transactional
    @Override

        public void addSponsorFromEvent (AssignToEventRequest assignToEventRequest){
            Optional<Evenement> c = eventRepo.findById(assignToEventRequest.getNumEvent());
            Long[] sponsIds = assignToEventRequest.getNumSponsor();
            List<Sponsor> sponsors = new ArrayList<>();
            if (c.isPresent()) {
                for (int i = 0; i < sponsIds.length; i++) {
                    Optional<Sponsor> p = sponsorRepo.findById(sponsIds[i]);
                    if (p.isPresent()) {
                        sponsors.add(p.get());
                    }
                }
                c.get().getSponsors().addAll(sponsors);
                eventRepo.save(c.get());
            }
        }


    @Override
    public void removeSponsorFromEvent(RemoveSponsorFromEventRequest req) {
        Optional<Evenement> c = eventRepo.findById(req.getNumEvent());
        if (c.isPresent()) {
            for (Iterator<Sponsor> iterator = c.get().getSponsors().iterator(); iterator.hasNext();) {
                Sponsor p = iterator.next();
                if (p.getNumSponsor() == req.getNumSponsor()) {
                    iterator.remove();
                }
            }
            eventRepo.save(c.get());
        }
    }


    @Override
    public List<Evenement> retrieveReservationsByUser(Long numUser) {
        return eventRepo.getEventsByUser(numUser);
    }

    @Override
    public List<User> retrieveUsersByEvent(Long numEvent) {

        Optional<Evenement> c = eventRepo.findById(numEvent);
        if (c.isPresent()) {
            return c.get().getUsers();
        } else {
            return Collections.emptyList();
        }
    }

    public int countActiveEvents() {
        List<Evenement> activeEvents = eventRepo.getActiveEvents();
        return activeEvents.size();
    }

    @Override
    public int countSoldOutEvents() {
            List<Evenement> events = (List<Evenement>) eventRepo.findAll();
            return (int) events.stream()
                    .filter(event -> event.getUsers().size() >= event.getNbrPlace())
                    .count();
        }


    @Override
    public AccueilStat pageAccueil() {
        int count = countActiveEvents();
        AccueilStat stats = new AccueilStat();
        stats.setTotalEvent(eventRepo.count());
        stats.setActivEvent(count);
        return stats;
    }

}
