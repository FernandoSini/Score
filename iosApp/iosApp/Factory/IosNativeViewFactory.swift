//
// Created by Fernando Fazio Sinigaglia on 02/09/25.
//

import Foundation
import SwiftUI
import ComposeApp
import StoreKit

class IosNativeViewFactory: NativeViewFactory {
    static var shared = IosNativeViewFactory()

    func openModal(title: String, onClick: @escaping () -> Void) -> UIViewController {
        let view = SheetView(title: title, onDismiss: {}, onClick: { value in print(value) })
        // let view = TesteView(isSheetPresented: isPresented)
        //let composeView = ComposeView().makeUIViewController(context: UIViewControllerRepresentableContext<ComposeView>())

        return UIHostingController(rootView: view)

    }

    // Implement required methods/properties from NativeViewFactory protocol here


}

struct SheetView: View {
    var offers: [String] = ["Plano Mensal - R$ 9,90 por mês", "Plano Anual - R$ 59,90 por ano"]
    var title: String
    var onDismiss: () -> Void
    var onClick: (Bool) -> Void
    @State private var isSheetPresented = false
    @State private var presentantionSelected: PresentationDetent = .large
    @State private var isOn: Bool = false;
    @State private var selectedOfferIndex: Int = 0

    var body: some View {
        Button(title) {
            isSheetPresented = !isSheetPresented
            onClick(isSheetPresented)
        }
        .sheet(isPresented: $isSheetPresented) {
            VStack() {

                HStack() {
                    Spacer()
                    Button(action: {
                        isSheetPresented = false
                        onClick(isSheetPresented)
                    }) {
                        Image(systemName: "xmark")
                            .foregroundColor(.primary)
                            .padding()
                    }
                }
                VStack {
                    Text("Sheet Title")
                        .font(.title)
                        .fontWeight(.bold)
                        .padding()
                    Text("Subscribe to our premium plan to unlock all features and enjoy an ad-free experience!")
                        .font(.body)
                        .multilineTextAlignment(.center)
                        .padding([.leading, .trailing], 20)
                    GeometryReader { geometry in
                        List(0..<offers.count) { index in
                            VStack() {
                                HStack() {
                                    VStack(alignment: .leading) {
                                        Text("\(Text("\(offers[index].prefix(12))"))")
                                            .font(.title).bold()
                                        Text("\(offers[index].suffix(offers[index].count - 14))")
                                            .font(.subheadline)
                                            .foregroundColor(.gray)
                                    }
                                   // .padding([.leading], 20)

                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    Toggle(isOn: Binding(
                                        get: { selectedOfferIndex == index },
                                        set: { newValue in
                                            if newValue {
                                                selectedOfferIndex = index
                                            }
                                        })) {
                                    }
                                    .onTapGesture {
                                        selectedOfferIndex = index
                                    }
                                    .toggleStyle(CheckBoxStyle())
                                    .frame(maxWidth: geometry.size.width * 0.1, alignment: .center)

                                }
                                .padding([.leading, .trailing], 20)
                                .frame(width: geometry.size.width * 0.9, height: 100)
                            }
                            .background(.ultraThinMaterial)
                            .onTapGesture {
                                selectedOfferIndex = index
                            }
                            .cornerRadius(10)
                            .shadow(color: .black.opacity(0.2), radius: 8, x: 0, y: 4)
                            .padding([.leading, .trailing], 20)
                            .listRowBackground(Color.clear)
                        }
                        .listStyle(PlainListStyle())
                    }


                }
                Button("Fechar") {
                    isSheetPresented = !isSheetPresented
                    onClick(isSheetPresented)
                }
                .padding()
                Spacer()

            }
            .background(Image("iap/iapbackground2")
                            .resizable()
                            .scaledToFill()
                            .ignoresSafeArea()
            )
            .presentationDetents([.large, .medium], selection: $presentantionSelected)
            .presentationSizing(.fitted)
            .presentationBackground(
                LinearGradient(
                    gradient: Gradient(colors: [Color(red: 0.25, green: 0.32, blue: 0.55), Color(red: 0.13, green: 0.19, blue: 0.36)]),
                    startPoint: .top,
                    endPoint: .bottom
                ).opacity(
                    self.presentantionSelected == .large ? 0.9 : 0.5)
            )
            .interactiveDismissDisabled(true)
        }

    }
}


struct SheetView1: View {
    var onDismiss: () -> Void
    var title: String
    var action: () -> Void
    @State private var isPresented: Bool

    init(onDismiss: @escaping () -> Void, title: String, action: @escaping () -> Void, isPresented: Bool) {
        self.onDismiss = onDismiss
        self.title = title
        self.action = action
        self._isPresented = State(initialValue: isPresented)
    }

    var body: some View {
        VStack {
            Text(title)
                .font(.title)
                .padding()
            Button("Fechar") {
                action()
            }
            Button("close") {
                onDismiss()
            }
            .padding()
        }
        .background(Color.indigo)
        .sheet(isPresented: $isPresented, onDismiss: onDismiss) {
            VStack {
                Text(title)
                    .font(.title)
                    .padding()
                Button("Fechar") {
                    action()
                }
                Button("close") {
                    onDismiss()
                }
                .padding()
            }
            .presentationDetents([.medium, .large])
            .presentationBackground(.thinMaterial)
        }
    }
}

struct SimpleIOSButton: View {
    var label: String
    var action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(label)
                .font(.headline)
                .foregroundColor(.blue)

        }
    }
}

struct CheckBoxStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        HStack {
            configuration.label
            Spacer()
            Image(systemName: configuration.isOn ? "checkmark.circle.fill" : "circle")
                .resizable()
                .frame(width: 24, height: 24)
                .foregroundColor(configuration.isOn ? .blue : .gray)
                .onTapGesture {
                    configuration.isOn.toggle()
                }
        }
        .padding()
    }
}


